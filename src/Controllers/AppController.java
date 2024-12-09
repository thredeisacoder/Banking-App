package Controllers;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Models.Account;
import Models.Notification;
import Models.Transaction;
import Views.DetailUserPage;
import Views.HomePage;
import Views.LoginPage;
import Views.NotificationDetailPage;
import Views.RegisterPage;
import Models.User;
import Views.notificationPage;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class AppController {

    User user;
    Account account;
    List<Transaction> transactionList;
    List<Notification> notificationList;
    LoginPage loginPage;
    HomePage homePage;
    Views.notificationPage notificationPage;
    RegisterPage registerPage;

    DetailUserPage detailUserPage;

    public AppController() {
        startApp();
        try {
            ConnectDatabase.getConnection();
            System.out.println("connect to database successfully!!!");
        }
        catch (SQLException e) {
        	System.out.println(e);
		}
    }
    public void startApp() {
        changeToLoginPage();
    }
    public void hanleLogin(Map<String, String> userInput) {
        System.out.println(userInput);
        if (!validateLogin(userInput.get("phone"), userInput.get("password"))){
            JOptionPane.showMessageDialog(loginPage, "Invalid Input!!!");
            return;
        }
        user=User.getUser(userInput.get("phone"), userInput.get("password"));
        if(user!=null) {
            account=Account.getAccount(user.getId());
            transactionList=Transaction.getTransactionList(user.getId());
            notificationList=Notification.getNotificationList(user.getId());
            loginPage.dispose();
            changeToHomePage();
        }
        else{
            JOptionPane.showMessageDialog(loginPage, "User not found!!!");
        }
    }
    public void changeToLoginPage(){
        loginPage= new LoginPage();
        JButton loginButton = loginPage.getLoginBtn();
        loginButton.addActionListener(e -> hanleLogin(loginPage.getUserInput()));
        JButton registerBtn = loginPage.getRegisterBtn();
        registerBtn.addActionListener(e -> changeToRegisterPage());
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
        balanceLabel.setText(("Balance: "+account.getBalance())+"$");
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
        submitBtn.addActionListener(e -> hanleRegister(registerPage.getUserInput()));
        JButton turnBackBtn = registerPage.getTurnBackBtn();
        turnBackBtn.addActionListener(e->{
            registerPage.dispose();
            changeToLoginPage();
        });
        loginPage.dispose();
        registerPage.setVisible(true);
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

        JButton editBtn = detailUserPage.getEditBtn();

        JButton saveBtn = detailUserPage.getSaveBtn();

        JButton turnBackBtn= detailUserPage.getTurnBackBtn();
        turnBackBtn.addActionListener(e->{
            detailUserPage.dispose();
            changeToHomePage();
        });

        homePage.dispose();
    }

    public boolean validateLogin(String phone, String password) {
        if(phone.equals("") || password.equals("")) return false;
        else if(phone.length()!=10||password.length()<4) return false;
        else return true;
    }

    public boolean validateRegister(Map<String,String> userInput){
        if(!userInput.get("confirmPass").equals(userInput.get("password"))){
            return false;
        }
        else if(userInput.get("phone").length()!=10||userInput.get("password").length()<4) return false;
        else return true;
    }


    public void hanleRegister(Map<String, String> userInput){
        if(!validateRegister(userInput)){
            JOptionPane.showMessageDialog(registerPage, "Invalid Input!!!");
            return;
        }
        else if(User.exist(userInput.get("phone"))){
            JOptionPane.showMessageDialog(registerPage, "phone was registered!!!");
            return;
        }

        User newUser= new User(-1, userInput.get("name"), userInput.get("email"), userInput.get("password"), userInput.get("address"), userInput.get("phone"));
        if(User.addUser(newUser)){
            newUser = User.getUser(newUser.getPhone(),newUser.getPassword());
            Account newAccount = new Account(null,0,newUser.getId());
            if(Account.addAccount(newAccount)){
                JOptionPane.showMessageDialog(registerPage, "resgister successfully");
                registerPage.dispose();
                changeToLoginPage();
            }
            else{
                JOptionPane.showMessageDialog(registerPage, "error!!!");
            }
        }
        else{
            JOptionPane.showMessageDialog(registerPage, "error!!!");
        }
    }

    public void logout(){
        user=null;
        account=null;
        transactionList.clear();
        notificationList.clear();
        homePage.dispose();
        changeToLoginPage();
    }
}

