package com.yaren.sqlitedeneme

import android.annotation.SuppressLint
import android.content.ContentProviderClient
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

//veritabanını oluşturduğumuz kısım

val database_name = "Veritabanim"
val table_name = "Kullanicilar"
val col_name = "adisoyadi"
val col_age = "yasi"
val col_id = "id"

class  DataBaseHelper (var context: Context):SQLiteOpenHelper(context,
    database_name, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        //veritabanı oluştuğunda bir kez çalışır

        var createTable = " CREATE TABLE " +  table_name+"("+
                col_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                col_name + " VARCHAR(256) , "+
                col_age + " INTEGER)"//eğer tırnaktan sonra boşluk olmazsa hata verir
        // Buraya kadar olan kısımda veritabanı için tablomuzu oluşturduk

        db?.execSQL(createTable) //bu satır ile de tablonun oluşturulmasını sağladık


    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //veritabanı yükseltmek için kullanılır

    }

    //veri kaydetmek için fonk tanımlıyoruz

    fun insertData(kullanici: Kullanici){//buradaki kullanici class olan
        val db = this.writableDatabase// veritabanına veri kaydedebilir hale getirdik
        val cv = ContentValues()// verileri yazabiliyoruz
        cv.put(col_name, kullanici.adsoyad)
        cv.put(col_age, kullanici.yasi)
        var sonuc = db.insert(table_name, null, cv)
        if(sonuc == (-1).toLong()){
            Toast.makeText(context, "Hatalı", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(context, "Başarılı", Toast.LENGTH_LONG).show()
        }
    }

    //verileri okumak için fonksiyon tanımlıyoruz

    @SuppressLint("Range")
    fun readData(): MutableList<Kullanici> {
        var liste : MutableList<Kullanici> = ArrayList() // verilerin listeye aktarılmasını sağlayacak satır
        val db = this.readableDatabase //verilerin okunabilir olmasını sağladık
        var sorgu = "Select * from "+ table_name //table_name tablosunda bulunan tüm verilere erişebilmek için * kullandık
        var sonuc = db.rawQuery(sorgu,null)//sorgunun sonucunu ekledik ve null değerinden başlattık
        if(sonuc.moveToFirst()){ // sonuç değişkeninde bir veri varsa yani veritabanı boş değilse
            do {
                var kullanici = Kullanici()
                kullanici.id = sonuc.getString(sonuc.getColumnIndex(col_id)).toInt()
                kullanici.adsoyad = sonuc.getString(sonuc.getColumnIndex(col_name))
                kullanici.yasi = sonuc.getString(sonuc.getColumnIndex(col_age)).toInt()
                liste.add(kullanici)//kullanıcının içerisindeki verileri listeye aktardık

            }while(sonuc.moveToNext()) //sonraki veriye geç

        }
        sonuc.close()//sorguyu kapattık
        db.close()//veritabanını kapattık
        return liste//listeyi döndürdük
    }

    //verileri güncellemek için fonksiyon tanımlıyoruz
    @SuppressLint("Range")
    fun updateData(){
        val db = this.readableDatabase
        var sorgu = "Select * from $table_name"
        var sonuc = db.rawQuery(sorgu,null)
        if(sonuc.moveToFirst()){
            do{
                var cv = ContentValues()//değişiklikleri kaydedebilmek için oluşturduk
                cv.put(col_age, (sonuc.getInt(sonuc.getColumnIndex(col_age)))+1)//verileri toplu olarak çektiğimiz için tüm değerleri değiştirdik
                cv.put(col_name, (sonuc.getString(sonuc.getColumnIndex(col_name)))+" " + "Güncel")
                db.update(table_name, cv, "$col_id = ? AND $col_name = ?" , //tüm satırlar için aynı işlemi tekrar et anlamında
                arrayOf(sonuc.getString(sonuc.getColumnIndex(col_id)),
                    sonuc.getString(sonuc.getColumnIndex(col_name))))
            }
                while (sonuc.moveToNext())
        }
        sonuc.close()
        db.close()
    }

    //verilerin silinmesi için fonksiyon yazıyoruz
    fun deleteData(){
        val db = this.writableDatabase
        db.delete(table_name, null, null)
        db.close()
    }



}
