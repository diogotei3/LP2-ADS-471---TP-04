import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MyFrame extends Frame implements ActionListener {

    //elementos
    private Label lnome=new Label("Nome: ");
    private TextField tnome = new TextField();
    private Button bpes = new Button("Pesquisar");
    private Label lnome2=new Label("Nome: ");
    private TextField tnome2 = new TextField();
    private Label lsal=new Label("Salário: ");
    private TextField tsal= new TextField();
    private Label lcar=new Label("Cargo: ");
    private TextField tcar = new TextField();
    private Button bant = new Button("Anterior");
    private Button bprox = new Button("Próximo");

    public int i = 0;

    //frame
    MyFrame(){
        Frame f = new Frame();
            f.setTitle("TRABALHO PRÁTICO 4");
            f.setLocation(200, 200);
            f.setSize(400,200);
            f.setBackground(Color.LIGHT_GRAY);
            f.setVisible(true);
            f.addWindowListener(new FechaJanela());
            f.setLayout(new BorderLayout());

        Panel p1 = new Panel(new GridLayout(1,2, 1, 1));
            p1.add(lnome);
            p1.add(tnome);

        Panel p2 = new Panel(new GridBagLayout());
            bpes.setPreferredSize(new Dimension(100,25));
            p2.add(bpes);

        Panel p3 = new Panel(new GridLayout(4,2, 1, 1));
            p3.add(lnome2);
            p3.add(tnome2);
            p3.add(lsal);
            p3.add(tsal);
            p3.add(lcar);
            p3.add(tcar);
            p3.add(bant);
            p3.add(bprox);

        f.add(p1, BorderLayout.NORTH);
        f.add(p2, BorderLayout.CENTER);
        f.add(p3, BorderLayout.SOUTH);

        //eventos
        bpes.addActionListener(this);
        bant.addActionListener(this);
        bprox.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {

        String url = "jdbc:sqlserver://localhost:1433;databaseName=aulajava;encrypt=true;trustServerCertificate=true;";
        String username = "sa";
        String password = "sa";
        String salario;

        if (e.getSource() == bpes) {
            try
            {
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    Connection con = DriverManager.getConnection(url,username, password);
                    System.out.println ("Conexao OK");

                    Statement st = con.createStatement();
                    ResultSet rs;

                    String busca = tnome.getText();

                    rs = st.executeQuery("select nome_func, sal_func, ds_cargo from tbcargos, tbfuncs where tbcargos.cd_cargo = tbfuncs.cd_cargo AND nome_func LIKE '" + busca + "%';");
                    rs.next();

                    tnome2.setText(rs.getString(1));
                    salario = rs.getString(2);
                    salario = salario.substring(0, salario.length() - 1);
                    salario = salario.substring(0, salario.length() - 1);
                    tsal.setText("R$ " + salario);
                    tcar.setText(rs.getString(3));

                con.close();
            }
            catch (Exception err)
            {
                System.out.println("Erro: " + err.getMessage());
            }
        }

        if (e.getSource() == bant) {
            i--;
            if(i<=0) i=1;
            try
            {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection con = DriverManager.getConnection(url,username, password);
                Statement st = con.createStatement();
                ResultSet rs;

                rs = st.executeQuery("select nome_func, sal_func, ds_cargo from tbcargos, tbfuncs where tbcargos.cd_cargo = tbfuncs.cd_cargo;");

                for(int j=0; j<i; j++) {
                    rs.next();
                }

                tnome.setText("");
                tnome2.setText(rs.getString(1));
                salario = rs.getString(2);
                salario = salario.substring(0, salario.length() - 1);
                salario = salario.substring(0, salario.length() - 1);
                tsal.setText("R$ " + salario);
                tcar.setText(rs.getString(3));
            }
            catch (Exception err)
            {
                System.out.println("Erro: " + err.getMessage());
            }
        }

        if (e.getSource() == bprox) {
            i++;
            try
            {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection con = DriverManager.getConnection(url,username, password);
                Statement st = con.createStatement();
                ResultSet rs;

                rs = st.executeQuery("select nome_func, sal_func, ds_cargo from tbcargos, tbfuncs where tbcargos.cd_cargo = tbfuncs.cd_cargo;");
                for(int j=0; j<i; j++) {
                    rs.next();
                }

                tnome.setText("");
                tnome2.setText(rs.getString(1));
                salario = rs.getString(2);
                salario = salario.substring(0, salario.length() - 1);
                salario = salario.substring(0, salario.length() - 1);
                tsal.setText("R$ " + salario);
                tcar.setText(rs.getString(3));
            }
            catch (Exception err)
            {
                System.out.println("Erro: " + err.getMessage());
            }
        }
    }
}
