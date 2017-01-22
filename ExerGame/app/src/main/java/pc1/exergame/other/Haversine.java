package pc1.exergame.other;
/**
 * Class calculates distance between two points on earth using latitude and longitude
 *
 * ReF>
 * https://rosettacode.org/wiki/Haversine_formula#Java
*/
public class Haversine {
    private static final double radius = 6372.8; // In kilometers
    private double hav;

    /**
     *
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @return hav - Distance in km
     */
    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        hav = radius*c;
        return hav;
    }

    /**
     *
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @return String true or false
     */
    public String IsClose(double lat1, double lon1, double lat2, double lon2){
        haversine(lat1, lon1, lat2, lon2);

        if(hav <= 0.1){
            return "true";
        }else{
            return "false";
        }

    }
}