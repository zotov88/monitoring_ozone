package monitoring_ozone.constants;

public interface XPathConstants {

    String empty = "//*[@id=\"layoutPage\"]/div[1]/div[2]/div[1]/div/div[1]/div[1]/div[1]/h2";

    // название
    String title1 = "//*[@id=\"layoutPage\"]/div[1]/div[4]/div[2]/div/div/div[1]/div/h1";
    String title2 = "//*[@id=\"layoutPage\"]/div[1]/div[4]/div[3]/div[3]/div/div[6]/h1";
    String title3 = "//*[@id=\"layoutPage\"]/div[1]/div[4]/div[3]/div[3]/div/div[5]/h1";
    String title4 = "//*[@id=\"layoutPage\"]/div[1]/div[4]/div[3]/div[3]/div/div[4]/h1";
    String title5 = "//*[@id=\"layoutPage\"]/div[1]/div[4]/div[3]/div[3]/div/div[3]/h1";

    // цена по озон-карте
    String prs1 = "//*[@id=\"layoutPage\"]/div[1]/div[4]/div[3]/div[3]/div/div[13]/div/div[1]/div/div/div[1]/div[1]/button/span/div/div[1]/div/div/span";
    String prs2 = "//*[@id=\"layoutPage\"]/div[1]/div[4]/div[3]/div[3]/div/div[14]/div[1]/div[1]/div/div/div[1]/div[1]/button/span/div/div[1]/div/div/span";
    String prs3 = "//*[@id=\"layoutPage\"]/div[1]/div[4]/div[3]/div[3]/div/div[13]/div/div[1]/div/div/div[1]/div[1]/button/span/div/div[1]/div/div/span";
    String prs4 = "//*[@id=\"layoutPage\"]/div[1]/div[4]/div[3]/div[3]/div/div[15]/div/div[1]/div/div/div[1]/div[1]/button/span/div/div[1]/div/div/span";
    String prs5 = "//*[@id=\"layoutPage\"]/div[1]/div[4]/div[3]/div[3]/div/div[12]/div/div[1]/div/div/div[1]/div[1]/button/span/div/div[1]/div/div/span";
    String prs6 = "//*[@id=\"layoutPage\"]/div[1]/div[4]/div[3]/div[2]/div[2]/div[2]/div/div[1]/div/div/div[1]/div[1]/button/span/div/div[1]/div/div/span";
    String prs7 = "//*[@id=\"layoutPage\"]/div[1]/div[4]/div[3]/div[2]/div[2]/div/div/div[1]/div/div/div[1]/div[1]/button/span/div/div[1]/div/div/span";
    String prs8 = "//*[@id=\"layoutPage\"]/div[1]/div[4]/div[3]/div[3]/div/div[11]/div/div/div/div/div[1]/div[1]/button/span/div/div[1]/div/div/span";
    String prs9 = "//*[@id=\"layoutPage\"]/div[1]/div[4]/div[3]/div[3]/div/div[11]/div/div/div/div/div[1]/div/div/div[1]/span[1]";
    String prs10 = "//*[@id=\"layoutPage\"]/div[1]/div[4]/div[3]/div[3]/div/div[10]/div/div[1]/div/div/div[1]/div[1]/button/span/div/div[1]/div/div/span";

    //     без озон-карты
    String prs11 = "//*[@id=\"layoutPage\"]/div[1]/div[4]/div[3]/div[2]/div[2]/div/div/div[1]/div/div/div[1]/div/div/div[1]/span";
    String prs12 = "//*[@id=\"layoutPage\"]/div[1]/div[4]/div[3]/div[3]/div/div[13]/div/div[1]/div/div/div[1]/div/div/div[1]/span[1]";
    String prs13 = "//*[@id=\"layoutPage\"]/div[1]/div[4]/div[3]/div[3]/div/div[12]/div/div[1]/div/div/div[1]/div/div/div[1]/span[1]";
    String prs14 = "//*[@id=\"layoutPage\"]/div[1]/div[4]/div[3]/div[3]/div/div[10]/div/div[1]/div/div/div[1]/div/div/div[1]/span[1]";

    String[] TITLES = {empty, title1, title2, title3, title4, title5};
    String[] PRICES = {prs1, prs2, prs3, prs4, prs5, prs6, prs6, prs7, prs8, prs9, prs10, prs11, prs12, prs13, prs14};
//    String[] PRICES = {prs1};
}
