package x.co.tips

//https://github.com/tuttelikz/ReadCsvExample_Android

class T4Station(name: String, lon: Double, lat: Double) {
    val name: String = name
    var lon: Double = lon
    var lat: Double = lat

    override fun toString(): String {
        return "Station ${name} at Lon ${lon}, Lat ${lat}"
    }
}