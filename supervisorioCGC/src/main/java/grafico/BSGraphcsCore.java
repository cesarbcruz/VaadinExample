/*     */ package grafico;
/*     */ 
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Embedded;
/*     */ import com.vaadin.ui.Label;
/*     */ import com.vaadin.ui.Panel;
/*     */ import java.io.PrintStream;
/*     */ import java.net.URLEncoder;
/*     */ 
/*     */ public class BSGraphcsCore
/*     */ {
/*  20 */   private static BSGraphcsCore instance = null;
/*  21 */   private String url = "http://127.0.0.1:8080";
/*  22 */   public static int TPO_GRAPHCS_PIZZA = 1;
/*  23 */   public static int TPO_GRAPHCS_GAUGE = 2;
/*  24 */   public static int TPO_GRAPHCS_BARRA = 3;
/*     */ 
/*     */   public static BSGraphcsCore getInstance()
/*     */   {
/*  31 */     if (instance == null) {
/*  32 */       instance = new BSGraphcsCore();
/*     */     }
/*  34 */     return instance;
/*     */   }
/*     */ 
/*     */   public void addUrl(String url) {
/*  38 */     this.url = url;
/*     */   }
/*     */ 
/*     */   public Embedded getGraphcsPizza(String tituloGrafico, String[] titilo, String[] valor, int w, int h)
/*     */   {
/*     */     try
/*     */     {
/*  52 */       int tipo = TPO_GRAPHCS_PIZZA;
/*  53 */       Panel p = new Panel();
/*  54 */       StringBuffer sbLabel = new StringBuffer();
/*  55 */       for (int i = 0; i < titilo.length; i++) {
/*  56 */         String string = titilo[i];
/*  57 */         if (i < titilo.length - 1)
/*  58 */           sbLabel.append("" + string + ",");
/*     */         else {
/*  60 */           sbLabel.append("" + string + "");
/*     */         }
/*     */       }
/*     */ 
/*  64 */       StringBuffer sbValor = new StringBuffer();
/*  65 */       for (int i = 0; i < valor.length; i++) {
/*  66 */         String string = valor[i];
/*  67 */         if (i < valor.length - 1)
/*  68 */           sbValor.append("" + string + ",");
/*     */         else {
/*  70 */           sbValor.append("" + string + "");
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*  75 */       String urlParam = this.url + "/BSGraphcsWEB/graphcs?q=" + tipo + "&t=" + tituloGrafico + "&l=" + sbLabel.toString() + "&v=" + sbValor.toString() + "&w=" + w + "&h=" + h;
/*  76 */       System.out.println(urlParam);
                Embedded browser = new Embedded("", new ExternalResource(urlParam));
                browser.setType(Embedded.TYPE_BROWSER); 
                browser.setSizeFull();
/* 172 */       return browser;
/*     */     } catch (Exception ex) {
/*  81 */       ex.printStackTrace();
/*  82 */     }

                return new Embedded("erro");
/*     */   }
/*     */ 
/*     */   public Embedded getGraphcsGauge(String tituloGrafico, String[] titilo, String[] valor, int w, int h)
/*     */   {
/*     */     try
/*     */     {
/*  97 */       int tipo = TPO_GRAPHCS_GAUGE;
/*  98 */       Panel p = new Panel();
/*  99 */       StringBuffer sbLabel = new StringBuffer();
/* 100 */       for (int i = 0; i < titilo.length; i++) {
/* 101 */         String string = titilo[i];
/* 102 */         if (i < titilo.length - 1)
/* 103 */           sbLabel.append("" + string + ",");
/*     */         else {
/* 105 */           sbLabel.append("" + string + "");
/*     */         }
/*     */       }
/*     */ 
/* 109 */       StringBuffer sbValor = new StringBuffer();
/* 110 */       for (int i = 0; i < valor.length; i++) {
/* 111 */         String string = valor[i];
/* 112 */         if (i < valor.length - 1)
/* 113 */           sbValor.append("" + string + ",");
/*     */         else {
/* 115 */           sbValor.append("" + string + "");
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 120 */       String urlParam = this.url + "/BSGraphcsWEB/graphcs?q=" + tipo + "&t=" + tituloGrafico + "&l=" + sbLabel.toString() + "&v=" + sbValor.toString() + "&w=" + w + "&h=" + h;
/* 121 */       System.out.println(urlParam);

                Embedded browser = new Embedded("", new ExternalResource(urlParam));
                browser.setType(Embedded.TYPE_BROWSER);     
                browser.setSizeFull();
/* 172 */       return browser;
/*     */     } catch (Exception ex) {
/* 126 */       ex.printStackTrace();
/* 127 */     }
                return new Embedded("erro");
/*     */   }
/*     */ 
/*     */   public Embedded getGraphcsBarra(String tituloGrafico, String[] titilo, String[] valor, String labelY, String labelX, int w, int h)
/*     */   {
/*     */     try
/*     */     {
/* 144 */       int tipo = TPO_GRAPHCS_BARRA;
/* 146 */       StringBuffer sbLabel = new StringBuffer();
/* 147 */       for (int i = 0; i < titilo.length; i++) {
/* 148 */         String string = titilo[i];
/* 149 */         if (i < titilo.length - 1)
/* 150 */           sbLabel.append("" + string + ",");
/*     */         else {
/* 152 */           sbLabel.append("" + string + "");
/*     */         }
/*     */       }
/*     */ 
/* 156 */       StringBuffer sbValor = new StringBuffer();
/* 157 */       for (int i = 0; i < valor.length; i++) {
/* 158 */         String string = valor[i];
/* 159 */         if (i < valor.length - 1)
/* 160 */           sbValor.append("" + string + ",");
/*     */         else {
/* 162 */           sbValor.append("" + string + "");
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 168 */       String urlParam = this.url + "/BSGraphcsWEB/graphcs?q=" + tipo + "&t=" + URLEncoder.encode(tituloGrafico, "UTF-8") + "&l=" + URLEncoder.encode(sbLabel.toString(), "UTF-8") + "&v=" + sbValor.toString() + "&w=" + w + "&h=" + h + "&y=" + URLEncoder.encode(labelY, "UTF-8") + "&x=" + URLEncoder.encode(labelX, "UTF-8");
/* 169 */       System.out.println(urlParam);

                Embedded browser = new Embedded("", new ExternalResource(urlParam));
                browser.setType(Embedded.TYPE_BROWSER);
                browser.setSizeFull();
/* 172 */       return browser;
/*     */     } catch (Exception ex) {
/* 174 */       ex.printStackTrace();
/* 175 */     }
                return new Embedded("erro");
/*     */   }
/*     */ }

/* Location:           /home/cesar/Downloads/Cliente/BSGraphcs.jar
 * Qualified Name:     bsgraphcs.BSGraphcsCore
 * JD-Core Version:    0.6.2
 */