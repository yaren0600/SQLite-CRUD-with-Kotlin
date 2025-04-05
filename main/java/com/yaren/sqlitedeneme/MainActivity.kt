package com.yaren.sqlitedeneme

import android.os.Bundle
import android.view.inputmethod.InputBinding
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.yaren.sqlitedeneme.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        // 🔽 Burada binding initialize edilir
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val context = this
        val db = DataBaseHelper(context)

        binding.btnkaydet.setOnClickListener{
            var etadsoyad = binding.etadsoyad.text.toString()
            var etyas = binding.etyas.text.toString()
            if(etadsoyad.isNotEmpty() && etyas.isNotEmpty()){
                var kullanici = Kullanici(etadsoyad, etyas.toInt())
                db.insertData(kullanici)

            }
            else{
                Toast.makeText(applicationContext,"Lütfen boş alanları doldurun!!!", Toast.LENGTH_LONG).show()
            }
        }

        //verileri okumak için
        binding.btnoku.setOnClickListener{
            var data = db.readData()
            binding.tvsonuc.text = "" //textview içeriğinde başka veriler varsa boşalttık
            for(i in 0 until data.size){//tüm verileri dolaşması için
                binding.tvsonuc.append(data.get(i).id.toString() + " "
                +data.get(i).adsoyad + " "+ data.get(i).yasi + "\n") //verilerin alt alta yazılabilmesi için \n yaptık

            }
        }

        //verilerin güncellenmesi için
        binding.btnguncelle.setOnClickListener{
            db.updateData()
            binding.btnoku.performClick()//btn oku butonuna tıklanmasına gerek kalmadan güncelleme sonrasında hemen veriyi göstermesi için
        }

        //verilerin silinmesi için
        binding.btnsil.setOnClickListener{
            db.deleteData()
            binding.btnoku.performClick()//sildikten sonra ekranda göstersin
        }



    }
}