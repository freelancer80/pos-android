package pos.main.adapters;

public class Currency_Denominations {
	
	public double c1, c2, c3;
	 int inc=100;
	
	public void showCurrency(String value) {
		double totalAmount= Double.parseDouble(value);

		if(totalAmount>0.00){
			
			String a= ""+totalAmount;
			int i= a.indexOf(".");
			String hundereds="1"; 
			String firstdigitS= a.substring(0,1);
			
			for(int j=1; j<i; j++){
				hundereds= hundereds+"0";
				firstdigitS=firstdigitS+"0";
			}
			double hun_total= Double.parseDouble(hundereds);
			System.out.println("zerooos...."+hun_total+"...."+ firstdigitS);
			
			double firstdigit= Integer.parseInt(firstdigitS);
			
			//double c1= firstdigit;
			
			firstdigit= (firstdigit+hun_total);
			
			//double c2= firstdigit;
			firstdigit= firstdigit+hun_total;
			
			//double c3= firstdigit;
		}
		
	}

}
