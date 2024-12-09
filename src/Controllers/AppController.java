package Controllers;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Models.Account;
import Models.Bank;
import Models.Notification;
import Models.Transaction;
import Views.AuthenPage;
import Views.DetailUserPage;
import Views.HomePage;
import Views.LoginPage;
import Views.NotificationDetailPage;
import Views.RegisterPage;
import Views.ResetPassWordPage;
import Views.TransferPage;
import Models.User;
import Views.notificationPage;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;



public class AppController {

    User user;
    Account account;
    List<Transaction> transactionList;
    List<Notification> notificationList;
    LoginPage loginPage;
    HomePage homePage;
    Views.notificationPage notificationPage;
    TransferPage transferPage;
    RegisterPage registerPage;
    AuthenPage authenPage;
    ResetPassWordPage resetPassWordPage;
    DetailUserPage detailUserPage;

    public AppController() {
        startApp();
        try {
            ConnectDatabase.getConnection();
            System.out.println("Connect to database successfully!!!");
        }
        catch (SQLException e) {
            System.out.println(e);
        }
    }
    // login at first
    public void startApp() {
        changeToLoginPage();
    }
    // validate login input and redirect to home page
    public void hanleLogin(Map<String, String> userInput) {
        System.out.println(userInput);
        //validate phone and password
        if (!validateLogin(userInput.get("phone"), userInput.get("password"))){
            JOptionPane.showMessageDialog(loginPage, "Invalid Input!!!");
            return;
        }
        //get user matches phone and password -> save it to user variable
        user=User.getUser(userInput.get("phone"), userInput.get("password"));

        if(user!=null) {
            //found user in database -> redirect to home page with informations of that user
            account=Account.getAccount(user.getId());
            transactionList=Transaction.getTransactionList(user.getId());
            notificationList=Notification.getNotificationList(user.getId());
            loginPage.dispose();
            changeToHomePage();
        }
        else{//if user is not exist in database
            JOptionPane.showMessageDialog(loginPage, "User not found!!!");
        }
    }
    public void changeToLoginPage(){
        loginPage= new LoginPage();
        JButton loginButton = loginPage.getLoginBtn();
        loginButton.addActionListener(e -> {
            hanleLogin(loginPage.getUserInput());
        });
        JButton registerBtn = loginPage.getRegisterBtn();
        registerBtn.addActionListener(e ->{
            loginPage.dispose();
            changeToRegisterPage();
        });

        JButton resetPasswordBtn = loginPage.getResetPassBtn();
        resetPasswordBtn.addActionListener(e -> {
            loginPage.dispose();
            changeToAuthenPage();
        });
    }
    public void changeToHomePage(){
        homePage = new HomePage();
        JButton logoutBtn = homePage.getLogoutBtn();
        logoutBtn.addActionListener(e -> logout());
        JLabel nameLabel= homePage.getNameLabel();
        nameLabel.setText("Name: "+user.getName());
        JLabel accNoLabel = homePage.getAccNoLabel();
        accNoLabel.setText("Account Number: "+ account.getAccno());
        JLabel balanceLabel= homePage.getBalanceLabel();
        BigDecimal balance = new BigDecimal(account.getBalance());
        balanceLabel.setText(("Balance: "+balance.toString()+"$"));
        JButton userBtn = homePage.getDetailBtn();
        userBtn.addActionListener(e->{
            homePage.dispose();
            changeToDetailPage();
        });
        // chuyển đổi sang trang notification
        JButton notificationBtn = homePage.getNotificationBtn();
        notificationBtn.addActionListener(e->{
            homePage.dispose();
            changeToNotificationPage();
        });
        loginPage.dispose();
        homePage.setVisible(true);
        JButton transferButton = homePage.getTransferBtn();
        transferButton.addActionListener(e->{
            homePage.dispose();
            changeToTransferPage();
        });
    }

    public void changeToNotificationPage() {
        notificationPage = new notificationPage();
        JLabel nameValue = notificationPage.getNameValue();
        nameValue.setText(user.getName());
        JLabel accountValue = notificationPage.getAccountValue();
        accountValue.setText(account.getAccno());
        JLabel balanceValue = notificationPage.getBalanceValue();
        balanceValue.setText(account.getBalance() + "$");
        // load dữ liệu vào trong bảng
        refreshNotification();
        JButton allNotificationsButton = notificationPage.getAllNotificationsButton();
        allNotificationsButton.addActionListener(e -> {
            refreshNotification();
        });
        JButton debitNotificationsButton = notificationPage.getDebitNotificationsButton();
        debitNotificationsButton.addActionListener(e -> {
            refreshDebitNotification();
        });
        JButton creditNotificationsButton = notificationPage.getCreditNotificationsButton();
        creditNotificationsButton.addActionListener(e -> {
            refreshCreditNotification();
        });

        // khởi tạo nút quay lại
        JButton backButton = notificationPage.getBackButton();
        // thêm sự kiện cho nút quay lại
        backButton.addActionListener(e -> {
            notificationPage.dispose();
            changeToHomePage();
        });
        // khởi tạo nút refresh
        JButton refreshButton = notificationPage.getRefeshButton();
        // thêm phần sự kiện cho refresh
        refreshButton.addActionListener(e -> {
            refreshNotification();
        });
        // khởi tạo nút mark read
        JButton markReadButton = notificationPage.getMarkReadButton();
        // thêm sự kiện cho nút mark read
        markReadButton.addActionListener(e -> {
            markReadNotification();
        });
        // khởi tạo nút delete
        JButton deleteButton = notificationPage.getDeleteButton();
        // thêm sự kiện cho nút delete
        deleteButton.addActionListener(e -> {
            deleteNotification();
        });
        JTable notificationTable = notificationPage.getNotificationTable();
        notificationTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = notificationTable.getSelectedRow();
                    String date = notificationTable.getValueAt(row, 0).toString();
                    String type = notificationTable.getValueAt(row, 1).toString();
                    String message = notificationTable.getValueAt(row, 2).toString();
                    String status = notificationTable.getValueAt(row, 3).toString();
                    double amount = Double.parseDouble(notificationTable.getValueAt(row, 4).toString());
                    notificationPage.dispose();
                    changeToNotificationDetailPage(date, type, message, status, amount);
                }
            }
        });
        notificationPage.setVisible(true);
    }
    // thêm phương thúc mới để chuyển đến trang chi tiết thông báo
    public void changeToNotificationDetailPage(String date, String type, String message, String status, double amount){
        NotificationDetailPage detailPage= new NotificationDetailPage();
        detailPage.getDateValueLabel().setText(date);
        detailPage.getTypeValueLabel().setText(type);
        detailPage.getMessageValueArea().setText(message);
        detailPage.getAmountValueLabel().setText(amount + "$");
        detailPage.getStatusValueLabel().setText(status);
        // thêm nút quay lại
        JButton backButton = detailPage.getBackButton();
        backButton.addActionListener(e -> {
            detailPage.dispose();
            changeToNotificationPage();
        });
        // lấy ra các dòng được chọn
        int[] selectedRows = notificationPage.getNotificationTable().getSelectedRows();

        // kiểm tra xem có dòng nào được chọn không
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(notificationPage, "Vui lòng chọn thông báo cần đánh dấu đã đọc", "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        // lấy model của bảng
        DefaultTableModel model = (DefaultTableModel) notificationPage.getNotificationTable().getModel();
        // duyệt qua từng dòng được chọn
        for (int selectedRow : selectedRows) {
            // lấy ra giá trị của từng cột trong dòng đươc chọn
            String date1 = model.getValueAt(selectedRow, 0).toString(); // Cột 0: Ngày
            String type1 = model.getValueAt(selectedRow, 1).toString(); // Cột 1: Loại
            String message1 = model.getValueAt(selectedRow, 2).toString(); // Cột 2: Nội dung
            String status1 = model.getValueAt(selectedRow, 3).toString(); // Cột 3: Trạng thái

            System.out.println("Dòng " + selectedRow + ":");
            System.out.println("- Ngày: " + date1);
            System.out.println("- Loại: " + type1);
            System.out.println("- Nội dung: " + message1);
            System.out.println("- Trạng thái: " + status1);
            // Cập nhật trạng thái
            if (Notification.updateStatus(date1, type1, message1, "Đã đọc")) {
                System.out.println("Cập nhật thành công thông báo: " + message);
            } else {
                JOptionPane.showMessageDialog(notificationPage,
                        "Có lỗi xảy ra khi cập nhật thông báo",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        detailPage.setVisible(true);
    }
    // phương thức refresh debit notification
    public void refreshDebitNotification() {
        DefaultTableModel model = (DefaultTableModel) notificationPage.getNotificationTable().getModel();
        model.setRowCount(0);
        List<Notification> notifications = Notification.getNotificationList(user.getId());
        for (Notification notification : notifications) {
            if (notification.getAmount() < 0) {
                model.addRow(new Object[] {
                        notification.getDate(),
                        notification.getType(),
                        notification.getMessage(),
                        notification.isRead() ? "Đã đọc" : "Chưa đọc",
                        notification.getAmount()
                });
            }
        }
    }
    
    // phương thức refresh credit notification
    public void refreshCreditNotification() {
        DefaultTableModel model = (DefaultTableModel) notificationPage.getNotificationTable().getModel();
        model.setRowCount(0);
        List<Notification> notifications = Notification.getNotificationList(user.getId());
        for (Notification notification : notifications) {
            if (notification.getAmount() > 0) {
                model.addRow(new Object[] {
                        notification.getDate(),
                        notification.getType(),
                        notification.getMessage(),
                        notification.isRead() ? "Đã đọc" : "Chưa đọc",
                        notification.getAmount()
                });
            }
        }
    }
    // phương thức refresh notification
    public void refreshNotification() {
        DefaultTableModel model = (DefaultTableModel) notificationPage.getNotificationTable().getModel();
        model.setRowCount(0);
        List<Notification> notifications = Notification.getNotificationList(user.getId());
        for (Notification notification : notifications) {
            System.out.println(notification.isRead());
            model.addRow(new Object[] {
                    notification.getDate(),
                    notification.getType(),
                    notification.getMessage(),
                    notification.isRead() ? "Đã đọc" : "Chưa đọc",
                    notification.getAmount()
            });
        }
    }
    // phương thức mark read notification
    public void markReadNotification() {
        // lấy ra các dòng được chọn
        int[] selectedRows = notificationPage.getNotificationTable().getSelectedRows();
      

        //kiểm tra xem có dòng nào được chọn không
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(notificationPage, "Vui lòng chọn thông báo cần đánh dấu đã đọc", "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        //lấy model của bảng
        DefaultTableModel model = (DefaultTableModel) notificationPage.getNotificationTable().getModel();
        // duyệt qua từng dòng được chọn
        for (int selectedRow : selectedRows) {
            // lấy ra giá trị của từng cột trong dòng đươc chọn
            String date = model.getValueAt(selectedRow, 0).toString(); // Cột 0: Ngày
            String type = model.getValueAt(selectedRow, 1).toString(); // Cột 1: Loại
            String message = model.getValueAt(selectedRow, 2).toString(); // Cột 2: Nội dung
            String status = model.getValueAt(selectedRow, 3).toString(); // Cột 3: Trạng thái

            System.out.println("Dòng " + selectedRow + ":");
            System.out.println("- Ngày: " + date);
            System.out.println("- Loại: " + type);
            System.out.println("- Nội dung: " + message);
            System.out.println("- Trạng thái: " + status);
            // Cập nhật trạng thái
            if (Notification.updateStatus(date, type, message, "Đã đọc")) {
                System.out.println("Cập nhật thành công thông báo: " + message);
            } else {
                JOptionPane.showMessageDialog(notificationPage,
                        "Có lỗi xảy ra khi cập nhật thông báo",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        refreshNotification();
        // Thông báo thành công
        JOptionPane.showMessageDialog(notificationPage,
                "Đã đánh dấu đã đọc thành công",
                "Thông báo",
                JOptionPane.INFORMATION_MESSAGE);

    }
   
    public void deleteNotification() {
        int[] selectedRows = notificationPage.getNotificationTable().getSelectedRows();
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(notificationPage, "vui lòng chọn thông báo cần xóa", "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(notificationPage,
                "Bạn có chắc chắn muốn xóa các thông báo đã chọn?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            DefaultTableModel model = (DefaultTableModel) notificationPage.getNotificationTable().getModel();

            // Sắp xếp các index từ lớn đến nhỏ
            Arrays.sort(selectedRows);
            for (int i = selectedRows.length - 1; i >= 0; i--) {
                // Lấy thông tin thông báo từ row được chọn
                String date = model.getValueAt(selectedRows[i], 0).toString();
                String type = model.getValueAt(selectedRows[i], 1).toString();
                String message = model.getValueAt(selectedRows[i], 2).toString();

                // Cập nhật trạng thái đã xóa trong database
                if (Notification.markAsDeleted(date, type, message)) {
                    model.removeRow(selectedRows[i]);
                } else {
                    JOptionPane.showMessageDialog(notificationPage,
                            "Có lỗi xảy ra khi xóa thông báo",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            JOptionPane.showMessageDialog(notificationPage,
                    "Xóa thông báo thành công!",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        refreshNotification();
    }
    public void changeToRegisterPage(){
        registerPage = new RegisterPage();
        JButton submitBtn = registerPage.getSubmitBtn();

        JComboBox<String> bankNameInput = registerPage.getBankNameInput();
        ArrayList<String> bankNameLst = Bank.getBankList();
        for (String bankName : bankNameLst ) {
            bankNameInput.addItem(bankName);
        }

        submitBtn.addActionListener(e -> handleRegister(registerPage.getUserInput()));


        JButton turnBackBtn = registerPage.getTurnBackBtn();
        turnBackBtn.addActionListener(e->{
            registerPage.dispose();
            changeToLoginPage();
        });
    }

    public void changeToDetailPage(){
        detailUserPage= new DetailUserPage();

        JTextField nameField= detailUserPage.getNameField();
        nameField.setText(user.getName());
        JTextField phoneField= detailUserPage.getPhoneField();
        phoneField.setText(user.getPhone());
        JTextField emailField = detailUserPage.getEmailField();
        emailField.setText(user.getEmail());
        JTextField addressField = detailUserPage.getAddressField();
        addressField.setText(user.getAddress());

        JButton saveBtn= detailUserPage.getSaveBtn();
        JButton editBtn = detailUserPage.getEditBtn();
        JButton turnBackBtn= detailUserPage.getTurnBackBtn();
        JButton changePasswordBtn= detailUserPage.getChangePasswordBtn();

        changePasswordBtn.addActionListener(e->{
            detailUserPage.dispose();
            changeToResetPassPage(user.getPhone());
        });

        editBtn.addActionListener(e->{
            saveBtn.setVisible(true);
            editBtn.setVisible(false);
            turnBackBtn.setVisible(false);
            changePasswordBtn.setVisible(true);
            nameField.setEditable(true);
            emailField.setEditable(true);
            addressField.setEditable(true);
        });

        turnBackBtn.addActionListener(e->{
            detailUserPage.dispose();
            changeToHomePage();
        });
        saveBtn.addActionListener(e->{
            saveBtn.setVisible(false);
            editBtn.setVisible(true);
            turnBackBtn.setVisible(true);
            changePasswordBtn.setVisible(false);
            nameField.setEditable(false);
            emailField.setEditable(false);
            addressField.setEditable(false);
            handleEdit(nameField.getText(),phoneField.getText(),emailField.getText(),addressField.getText());
        });
    }

    public void handleEdit(String name,String phone,String email,String address){
        System.out.println(name+phone+email+address);

        if (!Constraints.validUsername(name)||!Constraints.validPhone(phone)||!Constraints.validAddress(email)||!Constraints.validAddress(address)){//invalid input
            detailUserPage.dispose();
            changeToDetailPage();
        } else if (name.equals(user.getName())&&phone.equals(user.getPhone())&&email.equals(user.getEmail())&&address.equals(user.getAddress())) {
            //if nothing changed -> do nothing
        } else if(User.updateUser(name,phone,email,address)){//update new information
            user=  User.getUser(user.getPhone(),user.getPassword());
            JOptionPane.showMessageDialog(detailUserPage, "Successfully updated!!!");
        }
        else{//if update error
            JOptionPane.showMessageDialog(detailUserPage, "Update failed!!!");
            detailUserPage.dispose();
            changeToDetailPage();
        }
    }
    public void changeToTransferPage(){
        transferPage = new TransferPage();
        JButton returnButton = transferPage.getReturnButton();
        returnButton.addActionListener(e->{
            transferPage.dispose();
            changeToHomePage();
        });

        /* Balance */
        JLabel balanceLabel = transferPage.getBalanceLabel();
        balanceLabel.setText("Số dư: "+account.getBalance()+"$");

        JLabel destionationOwnerLabel = transferPage.getDestionationOwnerLabel();
//        destionationOwnerLabel.setText();

        JButton checkAccountNumberButton = transferPage.getCheckAccountNumberButton();
        checkAccountNumberButton.addActionListener(e->{
            System.out.println("clicked");
            JTextField destinationInput = transferPage.getDestinationInput();
            String accno = destinationInput.getText();
            System.out.println(accno);
//            if(accno.equals(account.getAccno())){
//                return;
//            }
            String user_name = Account.getUserName(accno);
            System.out.println(user_name+"ok");
        });

        /**/
        JComboBox bankNameComboBox = transferPage.getBankNameComboBox();
        ArrayList<String> bankNameList = new ArrayList<>();
        bankNameList = Bank.getBankList();
        for(String bankName:bankNameList){
            bankNameComboBox.addItem(bankName);
        }

        /* Transfer button */
        JButton transferButton = transferPage.getTransferButton();
        transferButton.addActionListener(e->{
            handleTransferMoney();
        });

    }
    //create otp contains 6 numbers
    public String createOtp(){
        String otp = "";
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            int randomNumber = random.nextInt(10);
            otp = otp + String.valueOf(randomNumber);
        }
        return otp;
    }

    public void changeToAuthenPage(){
        authenPage = new AuthenPage();
        JTextField phoneInput = authenPage.getPhoneInput();
        JButton otpBtn = authenPage.getSendOtpBtn();
        JButton submitBtn = authenPage.getSubmitBtn();
        JTextField otpInput = authenPage.getOtpInput();
        JLabel timeLabel = authenPage.getTimeLabel();
        StringBuilder otp= new StringBuilder("");

        otpBtn.addActionListener(e->{
            String phone = phoneInput.getText();
            if(!Constraints.validPhone(phone)){//validate phone number
                JOptionPane.showMessageDialog(authenPage, "Phone number is incorrect");
                return;
            }
            if(!User.exist(phone)){//find user with phone number
                JOptionPane.showMessageDialog(authenPage, "User not found");
                return;
            }
            //prepare for sending otp
            SpeedSMSAPI sender = new SpeedSMSAPI();
            //create counter for 5 minutes
            Thread countDown=new Thread(()->{
                int seconds = 180;
                try {
                    while (seconds > 0) {
                        int minutes = seconds / 60;
                        int remainingSeconds = seconds % 60;

                        timeLabel.setText(minutes+":" +(remainingSeconds<10? "0"+ remainingSeconds :remainingSeconds));
                        Thread.sleep(1000);
                        seconds--;
                    }
                    otpBtn.setEnabled(true);
                    timeLabel.setText("");
                } catch (InterruptedException er) {
                    System.out.println("countdown error");
                }
            });
            try {
                otp.delete(0,otp.length());
                otp.append(createOtp());
                //sender.sendSMS(phone,otp.toString());
                if(otp.toString().equals("")){
                    throw new IOException();
                }
                otpBtn.setEnabled(false);
                countDown.start();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });

        submitBtn.addActionListener(e->{
            if(otp.toString().equals("")){
                JOptionPane.showMessageDialog(authenPage,"No otp found");
            }
            else if(otp.toString().equals(otpInput.getText())){//if otp is correct
                System.out.println(otp);
                authenPage.dispose();
                changeToResetPassPage(phoneInput.getText());
            }
            else{
                JOptionPane.showMessageDialog(authenPage, "OTP is incorrect");
            }
        });
    }

    public void changeToResetPassPage(String phone){
        resetPassWordPage= new ResetPassWordPage(phone);

        JPasswordField  passwordInput = resetPassWordPage.getPassword();
        JPasswordField confirmInput = resetPassWordPage.getConfirmPassWord();
        JButton submitBtn = resetPassWordPage.getSubmitBtn();

        submitBtn.addActionListener(e->{
            //validate password
            if(Constraints.validPassword(passwordInput.getText())&&passwordInput.getText().equals(confirmInput.getText())){
                if(User.updatePassword(phone,passwordInput.getText()))//if update success
                {
                    JOptionPane.showMessageDialog(detailUserPage,"Password changed successfully, please login again!");
                    resetPassWordPage.dispose();
                    changeToLoginPage();
                }
                else {//error update
                    JOptionPane.showMessageDialog(detailUserPage,"update password error");
                }

            }
            else{//password is not matched with confirm
                JOptionPane.showMessageDialog(detailUserPage,"Password not match");
            }
        });
    }


    public void handleRegister(Map<String, String> userInput){
        if(!validateRegister(userInput)){//validate input
            JOptionPane.showMessageDialog(registerPage, "Invalid Input!!!");
            return;
        }
        else if(User.exist(userInput.get("phone"))){//check exist phone number in database
            JOptionPane.showMessageDialog(registerPage, "phone was registered!!!");
            return;
        }
        //create new user
        User newUser= new User(-1, userInput.get("name"), userInput.get("email"), userInput.get("password"), userInput.get("address"), userInput.get("phone"));
        if(User.addUser(newUser)){//add user to database
            newUser = User.getUser(newUser.getPhone(),newUser.getPassword());
            //create account for new user
            Account newAccount = new Account(null,0,newUser.getId(), userInput.get("bankName"));
            if(Account.addAccount(newAccount)){//add account to database
                JOptionPane.showMessageDialog(registerPage, "resgister successfully");
                registerPage.dispose();
                changeToLoginPage();
            }
            else{
                JOptionPane.showMessageDialog(registerPage, "error!!");
            }
        }
        else{
            JOptionPane.showMessageDialog(registerPage, "error!!!");
        }
    }

    public void logout(){
        //reset all information about user
        user=null;
        account=null;
        transactionList.clear();
        notificationList.clear();
        homePage.dispose();
        changeToLoginPage();
    }
    public void handleTransferMoney(){
        /* Destination Number */
        JTextField destinationInput = transferPage.getDestinationInput();

        /* Money */
        JTextField getMoneyInput = transferPage.getMoneyInput();

        /* Contents */
        JTextField getContentsInput = transferPage.getContentsInput();

        /* Combobox Bank Names */
        JComboBox bankNameComboBox = transferPage.getBankNameComboBox();

        Map<String, String> input = new HashMap<>();
        input.put("source", account.getAccno());
        input.put("destination", destinationInput.getText());
        input.put("money", getMoneyInput.getText());
        input.put("contents", getContentsInput.getText());
        input.put("bankName", (String)bankNameComboBox.getSelectedItem());

        if(!validateTransfer(input)){
            JOptionPane.showMessageDialog(transferPage, "Invalid Input!!!");
            return;
        }
        if(!validateHandleMoney(input)){
            JOptionPane.showMessageDialog(transferPage, "Not enough money!!!");
            return;
        }

    }
    public boolean validateLogin(String phone, String password) {
        //validate phone and pass
        return /*Constraints.validPhone(phone) &&*/ Constraints.validPassword(password);
    }
    public boolean validateRegister(Map<String,String> userInput){
        System.out.println(userInput);
        if(!userInput.get("confirmPass").equals(userInput.get("password"))){
            return false;
        }
        else return (Constraints.validPhone(userInput.get("phone"))
                && Constraints.validPassword(userInput.get("password"))
                && Constraints.validAddress("address")
                && Constraints.validEmail(userInput.get("email"))
        );
    }
    public boolean validateTransfer(Map<String, String> userInput){
        if(userInput.get("money").equals("") || userInput.get("contents").equals("") || userInput.get("bankName").equals("")) return false;{
            return true;
        }
    }
    public boolean validateHandleMoney(Map<String, String> userInput){
        double money = Double.parseDouble(userInput.get("money"));
        if(money > account.getBalance()) return false;
        return true;
    }
}

