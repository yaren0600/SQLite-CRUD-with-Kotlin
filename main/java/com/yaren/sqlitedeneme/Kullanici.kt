package com.yaren.sqlitedeneme

class Kullanici {
    //bu sınıf bizim köprü sınıfımız olacak

    var id: Int = 0;
    var adsoyad: String =  "";
    var yasi: Int = 0;


    constructor(adsoyad: String, yasi: Int){
        this.adsoyad = adsoyad // this ile yukarıda tanımlanan veriyi alıp burada tanımladığımız veriye atadık
        this.yasi = yasi

    }

    constructor(){
        //veritabanından veri okurken kullanacağız
    }

}