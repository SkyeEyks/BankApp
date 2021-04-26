package com.skye;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.EventListener;
import java.util.Random;

public class BankGUI extends JFrame {
    public BankGUI()
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Skye's Bank App");
        this.setSize(500, 300);
        this.setLocationRelativeTo(null);

        this.add(navigation());
        this.setVisible(true);
    }

    private JTabbedPane navigation()
    {
        JTabbedPane nav = new JTabbedPane();

        nav.addTab("Create account", tabCreateAccount());

        nav.addTab("Check balance", tabCheckBalance());

        nav.addTab("Deposit", tabDeposit());

        nav.addTab("Withdraw", tabWithdraw());

        return nav;
    }

    private JPanel tabCreateAccount()
    {
        JPanel self = new JPanel();

        JLabel lblFName = new JLabel("First name");
        JTextField txtFName = new JTextField();

        JLabel lblLName = new JLabel("Last name");
        JTextField txtLName = new JTextField();

        JButton btnCreateAcc = new JButton("Create account");
        btnCreateAcc.addActionListener(new AddAccount(txtFName, txtLName));

        JLabel lblStatus = new JLabel();

        self.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        Insets iEmpty = new Insets(0, 50, 0, 50);
        Insets iSpace = new Insets(0, 50, 25, 50);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.gridy = 0;
        gbc.insets = iEmpty;
        self.add(lblFName, gbc);

        gbc.gridy++;
        gbc.insets = iSpace;
        self.add(txtFName, gbc);

        gbc.gridy++;
        gbc.insets = iEmpty;
        self.add(lblLName, gbc);

        gbc.gridy++;
        gbc.insets = iSpace;
        self.add(txtLName, gbc);

        gbc.gridy++;
        gbc.insets = iEmpty;
        self.add(btnCreateAcc, gbc);

        gbc.gridy++;
        self.add(lblStatus, gbc);

        return self;
    }

    private JPanel tabCheckBalance()
    {
        JPanel self = new JPanel();

        JLabel lblID = new JLabel("Account ID");
        JTextField txtID = new JTextField();
        JButton btnSubmit = new JButton("Check balance");
        btnSubmit.addActionListener(new ShowBalance(txtID));

        self.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        Insets iEmpty = new Insets(0, 50, 0, 50);
        Insets iSpace = new Insets(0, 50, 25, 50);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.gridy = 0;
        gbc.insets = iEmpty;
        self.add(lblID, gbc);

        gbc.gridy++;
        gbc.insets = iSpace;
        self.add(txtID, gbc);

        gbc.gridy++;
        gbc.insets = iEmpty;
        self.add(btnSubmit, gbc);

        return self;
    }

    private JPanel tabDeposit()
    {
        JPanel self = new JPanel();

        JLabel lblID = new JLabel("Account ID");
        JTextField txtID = new JTextField();

        JLabel lblAmount = new JLabel("Amount");
        JTextField txtAmount = new JTextField();

        JButton btnSubmit = new JButton("Deposit");
        btnSubmit.addActionListener(new Deposit(txtID, txtAmount, 1));

        JLabel lblStatus = new JLabel();

        self.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        Insets iEmpty = new Insets(0, 50, 0, 50);
        Insets iSpace = new Insets(0, 50, 25, 50);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.gridy = 0;
        gbc.insets = iEmpty;
        self.add(lblID, gbc);

        gbc.gridy++;
        gbc.insets = iSpace;
        self.add(txtID, gbc);

        gbc.gridy++;
        gbc.insets = iEmpty;
        self.add(lblAmount, gbc);

        gbc.gridy++;
        gbc.insets = iSpace;
        self.add(txtAmount, gbc);

        gbc.gridy++;
        gbc.insets = iEmpty;
        self.add(btnSubmit, gbc);

        gbc.gridy++;
        self.add(lblStatus, gbc);

        return self;
    }

    private JPanel tabWithdraw()
    {
        JPanel self = new JPanel();

        JLabel lblID = new JLabel("Account ID");
        JTextField txtID = new JTextField();

        JLabel lblAmount = new JLabel("Amount");
        JTextField txtAmount = new JTextField();

        JButton btnSubmit = new JButton("Withdraw");
        btnSubmit.addActionListener(new Deposit(txtID, txtAmount, -1));

        JLabel lblStatus = new JLabel();

        self.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        Insets iEmpty = new Insets(0, 50, 0, 50);
        Insets iSpace = new Insets(0, 50, 25, 50);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.gridy = 0;
        gbc.insets = iEmpty;
        self.add(lblID, gbc);

        gbc.gridy++;
        gbc.insets = iSpace;
        self.add(txtID, gbc);

        gbc.gridy++;
        gbc.insets = iEmpty;
        self.add(lblAmount, gbc);

        gbc.gridy++;
        gbc.insets = iSpace;
        self.add(txtAmount, gbc);

        gbc.gridy++;
        gbc.insets = iEmpty;
        self.add(btnSubmit, gbc);

        gbc.gridy++;
        self.add(lblStatus, gbc);

        return self;
    }

    static class AddAccount implements ActionListener {
        private final JTextField fName, lName;

        public AddAccount(JTextField fName, JTextField lName)
        {
            this.fName = fName;
            this.lName = lName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int id = new Random().nextInt();
            id = Math.abs(id);
            try {
                DatabaseManager.createAccount(id, fName.getText(), lName.getText());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, String.format("Your account id is %d", id));
        }
    }

    static class ShowBalance implements ActionListener
    {
        private final JTextField accId;

        public ShowBalance(JTextField accId)
        {
            this.accId = accId;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int id = Integer.parseInt(accId.getText());
            double bal = -1;
            try {
                bal = DatabaseManager.getBalance(id);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            if (bal != -1)
                JOptionPane.showMessageDialog(null, String.format("Your balance is: %.2f", bal));
            else
                JOptionPane.showMessageDialog(null, "That account does not exist");
        }
    }

    static class Deposit implements ActionListener
    {
        private final JTextField accId, amount;
        private final int type;

        public Deposit(JTextField accId, JTextField amount, int type)
        {
            this.accId = accId;
            this.amount = amount;
            this.type = type;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int id = Integer.parseInt(accId.getText());
            double amt = Double.parseDouble(amount.getText()) * type;
            try {
                if(DatabaseManager.deposit(id, amt)) {
                    JOptionPane.showMessageDialog(null, "Your balance has been updated");
                } else {
                    JOptionPane.showMessageDialog(null, "Unable to update your balance");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
