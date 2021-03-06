package educationManagementSystemGUI.cabinets.admin.group;

import educationManagementSystemGUI.cabinets.admin.AdminCabinet;
import educationManagementSystemGUI.forms.LoginForm;
import educationManagementSystemGUI.utils.HttpLogout;
import educationManagementSystemGUI.utils.HttpPostUtil;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Класс {@link AdminCabinetAddTeacherToGroup} отображает форму метода
 * {@link AdminCabinet# groupAddButton}  пользователя с
 * ролью ADMIN.
 *
 * @author habatoo, dmitriysamus
 * @version 0.001
 */
public class AdminCabinetAddTeacherToGroup extends JFrame implements ActionListener {

    JSONObject userInfo;
    JSONObject response;
    Container container = getContentPane();
    JButton logoutButton = new JButton("Logout");
    JButton backButton = new JButton("Back");

    JLabel methodLabel = new JLabel("Adding Teacher to Group");
    JLabel teacherIdLabel = new JLabel("Teacher Id");
    JTextField teacherIdTextField = new JTextField();
    JLabel groupIdLabel = new JLabel("Group Id");
    JTextField groupIdTextField = new JTextField();
    JLabel groupLabel = new JLabel("Working with Group");
    JButton groupAddButton = new JButton("Add Teacher to Group");
    // при http POST запросе по адресу .../api/auth/groups/{groupNum}/{teacher}

    AdminCabinetAddTeacherToGroup(JSONObject userInfo, JSONObject response) {
        this.userInfo = userInfo;
        this.response = response;
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
    }

    /**
     * Метод {@link AdminCabinetAddTeacherToGroup#setLayoutManager}
     * устанавливает формат  layout manager
     * по умолчанию.
     */
    public void setLayoutManager() {
        container.setLayout(null);
    }

    /**
     * Метод {@link AdminCabinetAddTeacherToGroup#setLocationAndSize}
     * ориентирует месторасположение элементов
     * формы кабинета пользователя ADMIN.
     */
    public void setLocationAndSize() {
        logoutButton.setBounds(10, 530, 180, 30);
        backButton.setBounds(200, 530, 180, 30);

        methodLabel.setBounds(10, 100, 180, 30);
        teacherIdLabel.setBounds(10, 150, 180, 30);
        teacherIdTextField.setBounds(200, 150, 180, 30);
        groupIdLabel.setBounds(10, 200, 180, 30);
        groupIdTextField.setBounds(200, 200, 180, 30);

        groupLabel.setBounds(10, 50, 180, 30);
        groupAddButton.setBounds(10, 300, 180, 30);
    }

    /**
     * Метод {@link AdminCabinetAddTeacherToGroup#addComponentsToContainer}
     * добавляет элементовы формы кабинета пользователя ADMIN
     * в контейнер для отображения.
     */
    public void addComponentsToContainer() {
        container.add(logoutButton);
        container.add(backButton);

        container.add(methodLabel);
        container.add(teacherIdLabel);
        container.add(teacherIdTextField);
        container.add(groupIdLabel);
        container.add(groupIdTextField);

        container.add(groupLabel);
        container.add(groupAddButton);
    }

    /**
     * Метод {@link AdminCabinetAddTeacherToGroup#addActionEvent}
     * добавляет элементовы формы кабинета пользователя ADMIN
     * для реагирования на нажатия кнопок.
     */
    public void addActionEvent() {
        logoutButton.addActionListener(this);
        backButton.addActionListener(this);

        groupAddButton.addActionListener(this);
    }

    /**
     * Статический метод {@link AdminCabinetAddTeacherToGroup#showCabinetForm(JSONObject, JSONObject)}
     * создает форму кабинета пользователя ADMIN
     * для отображения.
     *
     * @param userInfo JSONObject
     * @param response JSONObject
     */
    public static void showCabinetForm(JSONObject userInfo, JSONObject response) {
        AdminCabinetAddTeacherToGroup frame = new AdminCabinetAddTeacherToGroup(userInfo, response);
        frame.setTitle("Admin Cabinet");
        frame.setVisible(true);
        frame.setBounds(10, 10, 400, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
    }

    /**
     * Метод {@link AdminCabinetAddTeacherToGroup#actionPerformed(ActionEvent)}
     * добавляет логику обработки при нажатии
     * <p>
     * кнопки добавления преподавателя в группу {@link AdminCabinetAddTeacherToGroup#groupAddButton}
     * кнопки возврата в кабинет пользователя ADMIN {@link AdminCabinetDeleteGroup#backButton}
     * кнопки выхода {@link AdminCabinetAddTeacherToGroup#logoutButton}
     *
     * @param e ActionEvent событие нажатия кнопки для обработки
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        //Coding Part of Add Teacher to Group button /api/auth/groups/{groupNum}/{teacher}
        if (e.getSource() == groupAddButton) {
            String groupNum;
            String teacher;
            groupNum = groupIdTextField.getText();
            teacher = teacherIdTextField.getText();
            String url = "http://localhost:8080/api/auth/groups/" + groupNum + "/" + teacher;

            JSONObject response = HttpPostUtil.httpRequest(url, "{}", (String) this.userInfo.get("accessToken"));
            if (null != response && response.get("message").equals("Teacher added successfully!")) {
                JOptionPane.showMessageDialog(this, "Teacher added successfully!" +
                        "\nTeacher id = " + teacher +
                        "\nGroup id = " + groupNum);
            } else if (null != response && response.get("message").equals("Error: User (teacher) does not exist!")) {
                JOptionPane.showMessageDialog(
                        this,
                        "Error: User (teacher) does not exist!",
                        "Admin error",
                        JOptionPane.ERROR_MESSAGE);

            } else if (null != response && response.get("message").equals("Error: Group does not exist!")) {
                JOptionPane.showMessageDialog(
                        this,
                        "Error: Group does not exist!",
                        "Admin error",
                        JOptionPane.ERROR_MESSAGE);

            } else if (null != response && response.get("message").equals("Error: User (teacher) has not role teacher!")) {
                JOptionPane.showMessageDialog(
                        this,
                        "Error: User (teacher) has not role teacher!",
                        "Admin error",
                        JOptionPane.ERROR_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Error: Teacher can't be added!",
                        "Admin error",
                        JOptionPane.ERROR_MESSAGE);
            }

            dispose();
            AdminCabinetAddTeacherToGroup.showCabinetForm(userInfo, response);

        }

        //Coding Part of BACK button
        if (e.getSource() == backButton) {
            dispose();
            AdminCabinet.showCabinetForm(userInfo, response);
        }

        //Coding Part of LOGOUT button
        if (e.getSource() == logoutButton) {
            JSONObject response = HttpLogout.httpLogout( userInfo);
            JOptionPane.showMessageDialog(this, response.get("message"));
            dispose();
            LoginForm.showLoginForm();
        }

    }

}
