package module1;
import java.util.*;
import java.awt.*;
public class Module1 {
    public static void main(String[] args) 
    {
        solve a=new solve();
    } 
}
class solve
{
    String str[];//Making Variables Global
    String x[];
    String y[];
    String x1[];
    String rar[];
    String rel[];
    String rel1[];
    Set<String> r;
    Set<String> k;
    Set<Set<String>> key;
    Set<String> f;
    Set<String> df;
    Set<String> can;
    Set<String> p;
    Set<String> np;
    Set<Set<String>> ckey1;
    Set<Set<String>> ckey;
    int n1,n2;
    public solve()
    {
        System.out.println("Enter the number of attributes");
        Scanner s=new Scanner(System.in);
        n1=s.nextInt();
        System.out.println("Enter the number of fd's");
        n2=s.nextInt();
        str=new String[n2];
        x=new String[n2];
        rel1=new String[n2];
        x1=new String[n2];
        y=new String[n2];
        rel=new String[n1];
        rar=new String[n1];
        r=new HashSet<>();
        k=new HashSet<>();
        can=new HashSet<>();
        df=new HashSet<>();
        ckey=new HashSet<>();
        ckey1=new HashSet<>();
        p=new HashSet<>();
        np=new HashSet<>();
        System.out.println("Enter the attributes");
        rar[0]=s.nextLine();
        for(int i=0;i<n1;i++)
        {
            rar[i]=s.nextLine();
            r.add(rar[i]);
            k.add(rar[i]);
        }
        System.out.println("Enter the fd's");
        f=new HashSet<>();
        for(int i=0;i<n2;i++)
        {
            str[i]=s.nextLine();//Scanning fd's and storing
            df.add(str[i]);
             StringTokenizer stoken=new StringTokenizer(str[i],"->");
             x[i]=stoken.nextToken();
             x1[i]=x[i];
             rel1[i]=x[i];
             y[i]=stoken.nextToken();
             String fd;//Creation of Set f acc to 15.2
            fd=x[i];
            StringTokenizer strtkn1=new StringTokenizer(x[i].replaceAll(""," ")," ");
            Set<String> tempi=new HashSet<>();
            while(strtkn1.hasMoreTokens())
            {
               tempi.add(strtkn1.nextToken());
            }
            ckey1.add(tempi);
            StringTokenizer strtkn=new StringTokenizer(y[i].replaceAll(""," ")," ");
            while(strtkn.hasMoreTokens())
            {
                String fd1=fd+"->";
                fd1=fd1+strtkn.nextToken();
                f.add(fd1);
            }
        }
        key=new HashSet<>();
        keyFind();
        ckeyFind();
        Iterator<Set<String>> k1=key.iterator();
        Iterator<Set<String>> k2=ckey.iterator();
        System.out.println("Super key(s)");
        while(k1.hasNext())
        {
            System.out.println(k1.next());
        }
        System.out.println("Candidate Key(s)");
        while(k2.hasNext())
        {
            System.out.println(k2.next());
        }
        Iterator<Set<String>> pi=ckey.iterator();
        while(pi.hasNext())
        {
            Set<String> pr=pi.next();
            p.addAll(pr);
        }
        np.addAll(r);
        np.removeAll(p);
        driver();
    }
    public void driver()
    {
        System.out.println("\nThe normal forms satisfied are:");
       boolean b=isTwoNF();
       boolean b1=isNf3();
       System.out.println("2-NF="+b);
       System.out.println("3-NF="+b1);
       if(b1)
       {
           boolean b2=bCheck();
           System.out.println("BCNF="+b2);
           if(b2)
           {
               System.out.println("The relation is already in BCNF");
           }
           else
           {
               System.out.println("\nDecomposition to BCNF:\n");
               mCover();
               bcnfCheck();
           }
       }
       else
       {
           System.out.println("BCNF=false");
           if(b)
           {
               System.out.println("\nDecomposition to 3NF:\n");
               mCover();
               nF3();
           }
           else
           {
               System.out.println("\nDecomposition to 2NF:\n");
               mCover();
               nF3();
           }
       }
    }
    public boolean isTwoNF()
    {
        Iterator<String> itp=f.iterator();
        while(itp.hasNext())
        {
            Set<String> D =new HashSet<>();// current fd's lhs
            Set<String> E =new HashSet<>();// rhs
            StringTokenizer sto4=new StringTokenizer(itp.next(),"->");
            StringTokenizer sto2=new StringTokenizer(sto4.nextToken().replaceAll(""," ")," ");
            StringTokenizer sto3=new StringTokenizer(sto4.nextToken().replaceAll(""," ")," ");
            while(sto2.hasMoreTokens())
            {
                D.add(sto2.nextToken());
            }
            while(sto3.hasMoreTokens())
            {
                E.add(sto3.nextToken());
            }
            if(np.containsAll(E))
            {
                Iterator<Set<String>> itr=ckey.iterator();
                while(itr.hasNext())
                {
                    Set<String> temp=itr.next();
                    if(temp.containsAll(D)&&!temp.equals(D))
                    {
                        return false;
                    }   
                }
            }
        }
        return true;
  }
    public boolean isNf3()
    {
        Iterator<String> temp=f.iterator();
        while(temp.hasNext())
        {
            Set<String> D =new HashSet<>();// current fd's lhs
            Set<String> E =new HashSet<>();// rhs
            StringTokenizer sto4=new StringTokenizer(temp.next(),"->");
            StringTokenizer sto2=new StringTokenizer(sto4.nextToken().replaceAll(""," ")," ");
            StringTokenizer sto3=new StringTokenizer(sto4.nextToken().replaceAll(""," ")," ");
            while(sto2.hasMoreTokens())
            {
                D.add(sto2.nextToken());
            }
            while(sto3.hasMoreTokens())
            {
                E.add(sto3.nextToken());
            }
            if(!key.contains(D))
            {
                if(!p.containsAll(E))
                {
                    return false;
                }
            }
        }
        return true;
    }
    public boolean bCheck()
    {
        if(key.containsAll(ckey1))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public void ckeyFind()
    {
        Iterator<String> rc=r.iterator();
        while(rc.hasNext())
        {
            Set<String> see=new HashSet<>();
            String trc=rc.next();
            int ctr=n1;
            Iterator<Set<String>> rc1=key.iterator();
            while(rc1.hasNext())
            {
                Set<String> use=rc1.next();
                if(use.contains(trc))
                {
                    if(use.size()<ctr)
                    {
                        ctr=use.size();
                        see.clear();
                        see.addAll(use);
                    }
                }
            }
            ckey.add(see);
        }
        Set<Set<String>> tem=new HashSet<>();
        tem.addAll(ckey);
        Set<Set<String>> tem3=new HashSet<>();
        tem3.addAll(ckey);
        Iterator<Set<String>> itm=tem.iterator();
        while(itm.hasNext())
        {
            Set<String> tem1=itm.next();
            Iterator<Set<String>> itm1=tem3.iterator();
            while(itm1.hasNext())
            {
                Set<String> tem2=itm1.next();
                if(tem2.containsAll(tem1)&&!tem1.equals(tem2))
                {
                    ckey.remove(tem2);
                }
            }
        }
    }
    public void nF3()
    {
        for(int i=0;i<n1;i++)
        {
           rel[i]="null"; 
        }
        for(int i=0;i<n2;i++)
        {
            Iterator<String> inf=f.iterator();
            while(inf.hasNext())
            {
                String nfc=inf.next();
                StringTokenizer nfc1=new StringTokenizer(nfc,"->");
                String te1=nfc1.nextToken();
                if(te1.equals(x[i]))
                {
                    rel[i]=x[i];
                    rel1[i]=rel1[i]+nfc1.nextToken();
                }
            }
        }
        int count=0;
        for(int i=0;i<n2;i++)
        {
            if(rel[i]!="null")
            {
                Set<String> temp=new HashSet<>();
                StringTokenizer temp1=new StringTokenizer(rel1[i].replaceAll(""," ")," ");
                while(temp1.hasMoreTokens())
                {
                    temp.add(temp1.nextToken());
                }
                if(key.contains(temp))
                {
                    count++;
                }
                System.out.println("\nRelation "+temp);
                System.out.println("Key is "+rel[i]);
                System.out.println("Fd's are");
                Iterator<String> inf2=f.iterator();
                while(inf2.hasNext())
                {
                    String nfc3=inf2.next();
                    StringTokenizer nfc4=new StringTokenizer(nfc3,"->");
                    if(nfc4.nextToken().equals(rel[i]))
                    {
                        System.out.println(nfc3);
                    }
                }
            }
        }
        if(count==0)
        {
            Iterator<Set<String>> citr=ckey.iterator();
            Set<String> citr1=citr.next();
            while(citr.hasNext())
            {
                citr1=citr.next();
            }
            System.out.println("\nRelation "+citr1);
            System.out.println("Key is "+citr1);
            System.out.println("Fd's are none");
        }
    }
    public void mCover()
    {
        Set<String> fnew1=new HashSet<>();
        fnew1.addAll(f);
        Iterator<String> itr=fnew1.iterator();
        while(itr.hasNext())
        {
            String c1=itr.next();
            StringTokenizer stkr=new StringTokenizer(c1,"->");
            String x=stkr.nextToken();
            StringTokenizer stkr1=new StringTokenizer(x.replaceAll(""," ")," ");
            String c2=stkr.nextToken();
            int i=0;
            while(i<x.length()&&x.length()!=1)
            {
                Set<String> copy=new HashSet<>();
                copy.addAll(f);
                copy.remove(c1);
                String z=stkr1.nextToken();
                String n=x;
                String c=n.replace(z,"");
                c=c+"->";
                c=c+c2;
                copy.add(c);
                if(eCheck(copy,f))
                {
                    if(eCheck(f,copy))
                    {
                        f.remove(c1);
                        f.add(c);
                    }
                }
                i++;
            }
        }
        Set<String> fnew=new HashSet<>();
        fnew.addAll(f);
        Iterator<String> itr1=fnew.iterator();
        while(itr1.hasNext())
        {
            Set<String> ftest=new HashSet<>();
            ftest.addAll(f);
            String fc=itr1.next();
            ftest.remove(fc);
            if(eCheck(ftest,f))
            {
                if(eCheck(f,ftest))
                {
                    f.remove(fc);
                }
            }
        }
    }
    public boolean eCheck(Set<String> f1,Set<String> f2)//Equivalence Check
    {
        Iterator<String> itr2=f1.iterator();
        while(itr2.hasNext())
        {
            String e1=itr2.next();
            StringTokenizer se1=new StringTokenizer(e1,"->");
            String p1=se1.nextToken();
            StringTokenizer se2=new StringTokenizer(p1.replaceAll(""," ")," ");
            Set<String> cp1=new HashSet<>();
            while(se2.hasMoreTokens())
            {
                cp1.add(se2.nextToken());
            }
            String p2=se1.nextToken();
            StringTokenizer se3=new StringTokenizer(p2.replaceAll(""," ")," ");
            Set<String> cp2=new HashSet<>();
            while(se3.hasMoreTokens())
            {
                cp2.add(se3.nextToken());
            }
            Set<String> ps=find(cp1,f2);
            if(!(ps.containsAll(cp2)))
            {
                return false;
            }
        }
        return(true);
    }
    public void keyFind()
    {
        int n=k.size();
        for (int i = 0; i < (1<<n); i++)
        {
            Set<String> k1=new HashSet<>();
            for (int j = 0; j < n; j++)
            {
                if ((i & (1 << j)) > 0) 
                {
                    k1.add(rar[j]);
                }
            }
            Set<String> cls=find(k1,f);
            if(cls.equals(r))
            {
                Set<String> copy=new HashSet<>();
                copy.addAll(k1);
                key.add(copy);
            }
        }
    }
    public Set<String> find(Set<String> s1,Set<String> s2)//Input of set x and fd's
    {   
            Set<String> names1 = new HashSet<>();
            names1.addAll(s1);
            Set<String> copy = new HashSet<>();
            copy.addAll(names1);
            for(int k=0;k<s2.size();k++)
            {
                Iterator<String> itr1=s2.iterator();
                for(int j=0;j<s2.size();j++)
                {
                    String bt=itr1.next();
                    StringTokenizer bt1=new StringTokenizer(bt,"->");
                    StringTokenizer sto1=new StringTokenizer((bt1.nextToken()).replaceAll(""," ")," ");
                    Set<String> names2 = new HashSet<>();
                    while(sto1.hasMoreTokens())
                    {
                        names2.add(sto1.nextToken());
                    }
                    StringTokenizer sto2=new StringTokenizer((bt1.nextToken()).replaceAll(""," ")," ");
                    Set<String> names3 = new HashSet<>();
                    while(sto2.hasMoreTokens())
                    {
                        names3.add(sto2.nextToken());
                    }
                    if(names1.containsAll(names2))
                    {
                        names1.addAll(names3);
                    }
                }
           }
        return(names1);
    }
    public void bcnfCheck()
    {
        Iterator<String> dfi=f.iterator();
        while(dfi.hasNext())
        {
            String temp=dfi.next();
            Set<String> th=new HashSet<>();
            Set<String> th1=new HashSet<>();
            StringTokenizer sth1=new StringTokenizer(temp,"->");
            StringTokenizer sth=new StringTokenizer((sth1.nextToken()).replaceAll(""," ")," ");
            while(sth.hasMoreTokens())
            {
                String str=sth.nextToken();
               th.add(str);
               th1.add(str);
            }
            StringTokenizer sth2=new StringTokenizer((sth1.nextToken()).replaceAll(""," ")," ");
            while(sth2.hasMoreTokens())
            {
               th.add(sth2.nextToken());
            }
            System.out.println("\nRelation "+th);
            System.out.println("Fd's are\n"+temp);
            System.out.println("key(s):"+th1);
        }
        ckey.removeAll(ckey1);
        Iterator<Set<String>> dfi1=ckey.iterator();
        while(dfi1.hasNext())
        {
            Set<String> td=dfi1.next();
            System.out.println("\nRelation "+td);
            System.out.println("Fd's are \n none");
            System.out.println("key is "+td);
        }
    }
}
