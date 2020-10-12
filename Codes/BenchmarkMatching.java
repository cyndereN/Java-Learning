    import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main {
  /**
   * Iterate through each line of input.
   */
   
  public static void main(String[] args) throws IOException {
    InputStreamReader reader = new InputStreamReader(System.in, StandardCharsets.UTF_8);
    BufferedReader in = new BufferedReader(reader);
    String line;
    while ((line = in.readLine()) != null) {
      String[] pfAndBm=line.split(":");
      String[] pfs=pfAndBm[0].split("\\|");
      String[] bms=pfAndBm[1].split("\\|");
      
      ArrayList<Asset> portfolio=new ArrayList<>();
      ArrayList<Asset> benchMarks=new ArrayList<>();
      
      
      
      for(int i=0;i<pfs.length;i++)
      {
        String[] pfsData=pfs[i].split(",");
        portfolio.add(new Asset(pfsData[0],pfsData[1],Integer.parseInt(pfsData[2]),Double.parseDouble(pfsData[3]),Double.parseDouble(pfsData[4])));
        
        String[] bmsData=bms[i].split(",");
        benchMarks.add(new Asset(bmsData[0],bmsData[1],Integer.parseInt(bmsData[2]),Double.parseDouble(bmsData[3]),Double.parseDouble(bmsData[4])));
      }

      List<Trade> transactions=transactions(portfolio,benchMarks);
      
      Collections.sort(transactions, new Comparator<Trade>(){
        @Override
         public int compare(Trade a, Trade b) {
         return a.getName().compareToIgnoreCase(b.getName());
          }
      });
      
      for(Trade t: transactions)
        System.out.println(t.getType()+","+t.getName()+","+t.getNumShares());
    }
  }
  
  public static double calculateTotalMarketValue(ArrayList<Asset> assets)
  {
    double totalMarketValue=0;
    for(int i=0;i<assets.size();i++)
    {
      Asset curAsset=assets.get(i);
      int shares=curAsset.getShares();
      double price=curAsset.getPrice();
      double accruedInt=curAsset.getaccInterest();

      if(curAsset.getType().equals("STOCK"))
      {
        totalMarketValue+=marketvalStock(shares,price);
      }
      else
      {
        totalMarketValue+=marketvalBond(shares,price,accruedInt);
      }
    }
    
    return totalMarketValue;
      
  }

  public static double marketvalStock(int shares, double price)
  {
    return shares*price;
  }

  public static double marketvalBond(int shares, double price, double accuredInterest)
  {
    return shares*(price+accuredInterest)*0.01;
  }
  

  public static double marketValPercentage(double marketVal,double totalMarketVal)
  {
    return (double)marketVal/totalMarketVal;
  }
  
  
  
  public static List<Trade> transactions( ArrayList<Asset> portfolio,ArrayList<Asset> benchMarks)
  {
    
      List<Trade>  numTransaction=new ArrayList<>();
    
      double totalMVPortfolio=calculateTotalMarketValue(portfolio);
      double totalMVBenchmarks=calculateTotalMarketValue(benchMarks);
      Collections.sort(portfolio, new LexicographicComparator());
      Collections.sort(benchMarks, new LexicographicComparator());
      for(int i=0; i<portfolio.size();i++)
      {
           Asset pAsset=portfolio.get(i);
           Asset bAsset=benchMarks.get(i);
           
           
          if(pAsset.getType().equals("STOCK"))
          {
             double MVPPortfolio=marketValPercentage(marketvalStock(pAsset.getShares(), pAsset.getPrice()),totalMVPortfolio);
             double MVPBenchMark=marketValPercentage(marketvalStock(bAsset.getShares(), bAsset.getPrice()),totalMVBenchmarks);
             
               if(MVPPortfolio<MVPBenchMark)
               {
                 int y=(int)Math.round((MVPBenchMark*totalMVPortfolio)/pAsset.getPrice()-pAsset.getShares());
                 numTransaction.add(new Trade("BUY",pAsset.getName(),y));
               }
               else if ( MVPPortfolio>MVPBenchMark)
               {
                  int y=(int)Math.round((MVPPortfolio*totalMVBenchmarks)/bAsset.getPrice()-bAsset.getShares());
                  numTransaction.add(new Trade("SELL",pAsset.getName(),y));
               }
           
            
          }
          else
          {
             double MVPPortfolio=marketValPercentage(marketvalBond(pAsset.getShares(), pAsset.getPrice(),pAsset.getaccInterest()),totalMVPortfolio);
             double MVPBenchMark=marketValPercentage(marketvalBond(bAsset.getShares(), bAsset.getPrice(),bAsset.getaccInterest()),totalMVBenchmarks);
               
               if(MVPPortfolio<MVPBenchMark)
               {
                 double numerator=(MVPBenchMark*totalMVPortfolio);
                 double denomenator=(pAsset.getPrice()+pAsset.getaccInterest())*0.01;
                 int y=(int)Math.round(numerator/denomenator-pAsset.getShares());
                 numTransaction.add(new Trade("BUY",pAsset.getName(),y));
               }
               else if( MVPPortfolio > MVPBenchMark)
               { 
                 double numerator=(MVPPortfolio*totalMVBenchmarks);
                 double denomenator=(bAsset.getPrice()+bAsset.getaccInterest())*0.01;
                 int y=(int)Math.round(numerator/denomenator-bAsset.getShares());
                 numTransaction.add(new Trade("SELL",pAsset.getName(),y));
               }
          }
      }
      
      return numTransaction;
    
  }

}

class LexicographicComparator implements Comparator<Asset> {
    @Override
    public int compare(Asset a, Asset b) {
        return a.getName().compareToIgnoreCase(b.getName());
    }
}


class Trade{
  
  private String type;
  private String tName;
  private int numShares;
  
  public Trade(String type, String name,int numShares)
  {
    this.type=type;
    this.tName=name;
    this.numShares=numShares;
  }
  
  public String getType()
  {
    return this.type;
  }
  public String getName()
  {
    return this.tName;
  }
  
  public int getNumShares()
  {
    return this.numShares;
  }
  
}


  class Asset{
     private String name;
     private String assetType;
     private  int shares;
     private double price;
     private double accruedInterest; 
    
    public Asset(String name, String assetType,int share,double price,double accInt)
    {
      this.name=name;
      this.assetType=assetType;
      this.shares=share;
      this.price=price;
      this.accruedInterest=accInt;
    }
    public String getName()
    {
      return this.name;
    }
    public String getType()
    {
      return this.assetType;
    }
    public int getShares()
    {
      return shares;
    }
    public double getPrice()
    {
      return price;
    }
    public double getaccInterest()
    {
      return this.accruedInterest;
    }
  }