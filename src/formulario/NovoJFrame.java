/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formulario;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Leto
 */
public class NovoJFrame extends javax.swing.JFrame {

    /**
     * Creates new form NovoJFrame
     */
    public NovoJFrame() {
        initComponents();
    }
    ArrayList<String> atributos = new ArrayList<String>();
    
    
    String obj = "";   
    String sql = "";
    String aux = "";
    
    public void gerarObj(){
          
        if (atributos.size() == 0)
        {          
            JOptionPane.showMessageDialog(null,"Sem atributos");
            return;
        }
        obj ="<?php \nclass " + nomeProprio(tabelaText.getText()) + "{\n";
        for (int i = 0; i< atributos.size();i++){
            obj = obj + "\tprivate $" + atributos.get(i) + ";\n";            
            aux = nomeProprio(atributos.get(i));
            obj = obj + "\tpublic function set" + aux + "($new"+aux+"){\n"
                    + "\t\t$this->" + atributos.get(i) + " = $new"+aux+";\n\t}\n"
                    + "\tpublic function get"+aux+"(){\n"
                    + "\t\treturn $this->" + atributos.get(i) + ";\n\t}\n\n";
             aux = "";
        }
        obj = obj + "\n}\n?>";
    }
    public String nomeProprio(String str){
        
            str = str.substring(0, 1).toUpperCase() + str.substring(1,str.length());
        
        
        return str;
    }
    public void gerarBd(){
        
        if (atributos.size() == 0)
        {          
            JOptionPane.showMessageDialog(null,"Sem atributos");
            return;
        }
        sql ="<?php\n" +
        "require_once(\"banco.php\");\n" +
        "require_once(\""+tabelaText.getText()+".php\");\n\n" +
        "class BD" + nomeProprio(tabelaText.getText())+"{\n"
                //
                //
                //Adicionar
                //
                //
          + "\tpublic function adicionar($"+ nomeProprio(tabelaText.getText())+"){\n "
              + "\t\t$conexao = banco::getConexao();\n";
        for (int i = 0; i  < atributos.size();i++){       
            aux = nomeProprio(atributos.get(i));
            sql = sql + "\t\t$"+atributos.get(i) + " = mysql_real_escape_string($" + nomeProprio(tabelaText.getText()) + "->get" + aux + "());\n";
        }
        sql = sql + "\t\t$sql = \"INSERT INTO "+tabelaText.getText() + " (";
        for (int i = 0; i  < atributos.size();i++){
            if (i != atributos.size()-1){
                sql = sql + atributos.get(i) + ", ";
            }
            else{                          
                sql = sql + atributos.get(i) + ") VALUES ("; //Quando for o ultimo
            }
        }
        for (int i = 0; i  < atributos.size();i++){
            if (i != atributos.size()-1){
                sql = sql + "'&" +atributos.get(i) + "', ";
            }
            else{                          
                sql = sql + "'$" + atributos.get(i) + "')\";"
                        + "\n\t\tmysql_query($sql, $conexao);\n\t}"; //Quando for o ultimo
            }
        }
        
        
        //
        //
        //Deletar
        //
        //
        sql = sql + "\n\n\t"
                + "public function deletar($"+ nomeProprio(tabelaText.getText())+"){\n "
                + "\t\t$conexao = banco::getConexao();\n";
            sql = sql + "\t\t$"+atributos.get(0) + " = mysql_real_escape_string($" + nomeProprio(tabelaText.getText()) + "->get" + nomeProprio(atributos.get(0)) + "());\n";
        
        sql = sql + "\t\t$sql = \"DELETE FROM  "+tabelaText.getText() + " WHERE "+atributos.get(0)+" = $"+ atributos.get(0) +"\";" 
                + "\n\t\tmysql_query($sql, $conexao);\n\t}";
        
        //
        //
        //Alterar
        //
        //
        sql = sql + "\n\n\t"
                + "public function alterar($"+ nomeProprio(tabelaText.getText())+"){\n "
                + "\t\t$conexao = banco::getConexao();\n";
        for (int i = 0; i  < atributos.size();i++){       
            aux = nomeProprio(atributos.get(i));
            sql = sql + "\t\t$"+atributos.get(i) + " = mysql_real_escape_string($" + nomeProprio(tabelaText.getText()) + "->get" + aux + "());\n";
        }
        sql = sql + "\t\t$sql = \"UPDATE "+tabelaText.getText() + " SET ";
        for (int i = 1; i  < atributos.size();i++){
            if (i != atributos.size()-1){
                sql = sql + atributos.get(i) + " = '$" + atributos.get(i)+"', ";
            }
            else{
                sql = sql + atributos.get(i) + " = '$" + atributos.get(i)+"' WHERE "  + atributos.get(0) + " = '$" + atributos.get(0)+"'\""
                        + "\n\t\tmysql_query($sql, $conexao);\n\t}";
            }
                    
        }
        
        //
        //
        //Lista
        //
        //
        sql = sql + "\n\n\t" + "public function listar() {\n" +
        "\t\t$conexao = banco::getConexao();\n" +
        "\t\t$sql = \"SELECT * FROM " + tabelaText.getText() +"\"\n" +
        "\t\t$consulta = mysql_query($sql, $conexao);\n" +
        "\t\t$resposta = array();\n" +
        "\t\twhile ($linha = mysql_fetch_array($consulta)) {"
                + "\n\t\t\t$" + tabelaText.getText() + " = new " + tabelaText.getText() + "();";
        for (int i = 0; i  < atributos.size();i++){
            sql = sql +  "\n\t\t\t$" + tabelaText.getText()+"->set"+nomeProprio(atributos.get(i))+"($linha[\"" + atributos.get(i) + "\"]);";
        }   
        sql = sql + "\n\t\t\t$resposta[] = $"+tabelaText.getText()+";\n" +
        "\t\t}\n";
        sql = sql + "\t" + "return $resposta;\n";
        sql = sql + "\t}\n}";
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        objText = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        atributoText = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        addButton = new javax.swing.JButton();
        tabelaText = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        repositorioText = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        bdButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextinstrucoes = new javax.swing.JTextArea();
        jComboTipo = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        objText.setColumns(20);
        objText.setRows(5);
        jScrollPane1.setViewportView(objText);

        jLabel3.setText("Nome da tabela:");

        atributoText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                atributoTextActionPerformed(evt);
            }
        });

        jLabel4.setText("Atributo:");

        addButton.setText("Adicionar");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        tabelaText.setText("tabela");

        repositorioText.setColumns(20);
        repositorioText.setRows(5);
        jScrollPane2.setViewportView(repositorioText);

        jLabel1.setText("Objeto");

        jLabel2.setText("Repositorio");

        bdButton.setText("Gerar Repositorio");
        bdButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bdButtonActionPerformed(evt);
            }
        });

        jButton1.setText("Limpar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel5.setText("Instruções: ");

        jTextinstrucoes.setColumns(20);
        jTextinstrucoes.setRows(5);
        jTextinstrucoes.setText("Insira o nome da tabela, em seguida o atributo que será sua chave primaria. Depois clieque em \"Adicionar\".\nAgora adicione os demais atributos. Depois de adicionar todos os atributos. clique em \"Gerar Repositorio\"\npara elaborar o codigo de conexão do objeto com o banco de dados. Por fim copie os itens dos textArea\nabaixo e adicione em seus arquivos *.php, depois é so clicar em \"Limpar\" para começar a cadastrar mais um Objeto;");
        jScrollPane3.setViewportView(jTextinstrucoes);

        jComboTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(atributoText, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                            .addComponent(jLabel3)
                            .addComponent(tabelaText, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                            .addComponent(jLabel4)
                            .addComponent(jComboTipo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(addButton))
                                .addGap(297, 297, 297)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(bdButton)
                                .addGap(193, 193, 193)
                                .addComponent(jButton1))
                            .addComponent(jLabel2)
                            .addComponent(jScrollPane2))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tabelaText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(atributoText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addButton)
                    .addComponent(bdButton)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed

        if (!"".equals(atributoText.getText())){
            for (int i = 0; i< atributos.size();i++) {
                if (atributos.get(i).equals(atributoText.getText())){
                    JOptionPane.showMessageDialog(null, "Atributo ja adicionado");
                    return;
                }
                
            }
            atributos.add(atributoText.getText());
            
        }
        gerarObj();
        objText.setText(obj);
        atributoText.setText("");
        tabelaText.enable(false);

    }//GEN-LAST:event_addButtonActionPerformed

    private void atributoTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_atributoTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_atributoTextActionPerformed

    private void bdButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bdButtonActionPerformed
        gerarBd();
        repositorioText.setText(sql);
        
    }//GEN-LAST:event_bdButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JOptionPane.showMessageDialog(null, "Campos limpos");
        atributos.clear();
        repositorioText.setText("");
        objText.setText("");
        tabelaText.setText("");
        tabelaText.enable(true);
        atributoText.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NovoJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NovoJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NovoJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NovoJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NovoJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JTextField atributoText;
    private javax.swing.JButton bdButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboTipo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextinstrucoes;
    private javax.swing.JTextArea objText;
    private javax.swing.JTextArea repositorioText;
    private javax.swing.JTextField tabelaText;
    // End of variables declaration//GEN-END:variables
}