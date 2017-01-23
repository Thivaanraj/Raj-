package pc1.exergame.other;

public class Haversine {
    private static final double radius = 6372.8; // In kilometers
    private double hav;

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

    public String IsClose(double lat1, double lon1, double lat2, double lon2){
        haversine(lat1, lon1, lat2, lon2);

        if(hav <= 0.05){
            return "true";
        }else{
            return "false";
        }

    }
}