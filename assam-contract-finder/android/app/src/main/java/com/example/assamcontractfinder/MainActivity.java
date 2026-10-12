package com.example.assamcontractfinder;
import android.app.*; import android.os.*; import android.content.*; import android.net.Uri; import android.view.*; import android.widget.*;
public class MainActivity extends Activity {
 LinearLayout list;
 public void onCreate(Bundle b){super.onCreate(b); LinearLayout root=new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setPadding(24,30,24,20);
 TextView h=new TextView(this); h.setText("Assam Contract Finder"); h.setTextSize(26); h.setTypeface(null,1); root.addView(h);
 TextView s=new TextView(this); s.setText("Assam Government + Railway/NFR"); root.addView(s);
 EditText q=new EditText(this); q.setHint("Search: electrical, civil, railway..."); root.addView(q);
 LinearLayout bs=new LinearLayout(this); String[] cs={"All","Electrical","Civil","Supply","Railway"}; for(String c:cs){Button x=new Button(this); x.setText(c); x.setOnClickListener(v->show(((Button)v).getText().toString())); bs.addView(x,new LinearLayout.LayoutParams(0,-2,1));} root.addView(bs);
 list=new LinearLayout(this); list.setOrientation(LinearLayout.VERTICAL); ScrollView sv=new ScrollView(this); sv.addView(list); root.addView(sv,new LinearLayout.LayoutParams(-1,0,1)); q.setOnEditorActionListener((v,a,e)->{show(q.getText().toString());return false;}); show("All"); setContentView(root); }
 void show(String c){list.removeAllViews(); if(c.equalsIgnoreCase("All")||c.toLowerCase().contains("electrical")) add("Assam Electrical Tenders","Current opportunities — official Assam portal","https://assamtenders.gov.in/"); if(c.equalsIgnoreCase("All")||c.toLowerCase().contains("civil")) add("Assam Civil Tenders","Current opportunities — official Assam portal","https://assamtenders.gov.in/"); if(c.equalsIgnoreCase("All")||c.toLowerCase().contains("railway")) add("Railway / NFR Tenders","Current opportunities — official IREPS portal","https://www.ireps.gov.in/"); if(c.equalsIgnoreCase("All")||c.toLowerCase().contains("supply")) add("Assam Supply Tenders","Current opportunities — official Assam portal","https://assamtenders.gov.in/"); }
 void add(String t,String d,String u){LinearLayout card=new LinearLayout(this); card.setOrientation(LinearLayout.VERTICAL); card.setPadding(18,18,18,18); TextView a=new TextView(this);a.setText(t);a.setTextSize(18);a.setTypeface(null,1); TextView z=new TextView(this);z.setText(d); Button b=new Button(this);b.setText("Open official tender portal");b.setOnClickListener(v->startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(u))));card.addView(a);card.addView(z);card.addView(b);list.addView(card);}
}
