import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.Scanner;

public class Login extends JFrame {
    private static final String CARD_LOGIN = "login";
    private static final String CARD_REGISTER = "register";

    // ---- Font sizes chuẩn cho 1200x880 ----
    private static final float FONT_TITLE = 56f;
    private static final float FONT_SUBTITLE = 20f;
    private static final float FONT_LABEL = 18f;
    private static final float FONT_FIELD = 18f;
    private static final float FONT_BUTTON = 18f;
    private static final float FONT_SECTION = 24f; // tiêu đề màn đăng ký
    private static final float FONT_FOOTER = 14f;

    private final File accountFile = new File("accounts.txt");

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardContainer = new JPanel(cardLayout);

    private JTextField loginUserField;
    private JPasswordField loginPassField;
    private JLabel loginStatus;

    private JTextField regUserField;
    private JPasswordField regPassField;
    private JLabel regStatus;

    public Login() {
        setTitle("Arkanoid — Login / Register");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800); // khớp yêu cầu
        setLocationRelativeTo(null);

        ensureAccountFile();

        GradientPanel root = new GradientPanel(new Color(32, 40, 70), new Color(58, 96, 115));
        root.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(24, 24, 24, 24);
        g.fill = GridBagConstraints.BOTH;
        g.weightx = 1;
        g.weighty = 1;

        RoundedPanel formHost = new RoundedPanel(28, Color.WHITE);
        formHost.setLayout(new BorderLayout());
        formHost.setBorder(new EmptyBorder(28, 36, 28, 36));

        // Header
        JLabel title = new JLabel("Arkanoid", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, FONT_TITLE));
        title.setForeground(new Color(32, 40, 70));
        JLabel subtitle = new JLabel("Sign in to play", SwingConstants.CENTER);
        subtitle.setFont(subtitle.getFont().deriveFont(FONT_SUBTITLE));
        subtitle.setForeground(new Color(90, 100, 120));
        subtitle.setBorder(new EmptyBorder(0, 0, 16, 0));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.add(title, BorderLayout.NORTH);
        header.add(subtitle, BorderLayout.SOUTH);

        // Cards
        cardContainer.setOpaque(false);
        cardContainer.add(createLoginCard(), CARD_LOGIN);
        cardContainer.add(createRegisterCard(), CARD_REGISTER);

        // Footer
        JLabel footer = new JLabel("© 2025 Arkanoid Demo — Java Swing", SwingConstants.CENTER);
        footer.setFont(footer.getFont().deriveFont(FONT_FOOTER));
        footer.setForeground(new Color(140, 150, 165));
        footer.setBorder(new EmptyBorder(16, 0, 0, 0));

        formHost.add(header, BorderLayout.NORTH);
        formHost.add(cardContainer, BorderLayout.CENTER);
        formHost.add(footer, BorderLayout.SOUTH);

        root.add(formHost, g);
        setContentPane(root);
        setVisible(true);
    }

    // ---------------- LOGIN (BoxLayout dọc) ----------------
    private JPanel createLoginCard() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(8, 8, 8, 8));

        JLabel userLb = label("Tài khoản");
        userLb.setFont(userLb.getFont().deriveFont(FONT_LABEL));
        loginUserField = new JTextField();
        loginUserField.setFont(loginUserField.getFont().deriveFont(FONT_FIELD));
        addEnterToClick(loginUserField, this::doLogin);

        JLabel passLb = label("Mật khẩu");
        passLb.setFont(passLb.getFont().deriveFont(FONT_LABEL));
        loginPassField = new JPasswordField();
        loginPassField.setFont(loginPassField.getFont().deriveFont(FONT_FIELD));
        addEnterToClick(loginPassField, this::doLogin);

        JCheckBox show = new JCheckBox("Hiện mật khẩu");
        show.setFont(show.getFont().deriveFont(FONT_LABEL - 2f));
        bindShowPassword(loginPassField, show);
        show.setAlignmentX(Component.LEFT_ALIGNMENT);
        show.setOpaque(false);

        RoundedButton btnLogin = new RoundedButton("Đăng nhập");
        btnLogin.setFont(btnLogin.getFont().deriveFont(Font.BOLD, FONT_BUTTON));
        btnLogin.addActionListener(e -> doLogin());

        RoundedButton btnToRegister = new RoundedButton("Tạo tài khoản");
        btnToRegister.setFont(btnToRegister.getFont().deriveFont(Font.BOLD, FONT_BUTTON));
        btnToRegister.setBackground(new Color(235, 239, 245));
        btnToRegister.setForeground(new Color(32, 40, 70));
        btnToRegister.addActionListener(e -> {
            regUserField.setText("");
            regPassField.setText("");
            regStatus.setText(" ");
            cardLayout.show(cardContainer, CARD_REGISTER);
        });

        loginStatus = new JLabel(" ");
        loginStatus.setFont(loginStatus.getFont().deriveFont(FONT_LABEL - 2f));
        loginStatus.setForeground(new Color(180, 50, 50));
        loginStatus.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(userLb);
        panel.add(space(8));
        panel.add(stylize(loginUserField));
        panel.add(space(16));
        panel.add(passLb);
        panel.add(space(8));
        panel.add(stylize(loginPassField));
        panel.add(space(10));
        panel.add(show);
        panel.add(space(22));
        panel.add(fillWidth(btnLogin));
        panel.add(space(14));
        panel.add(fillWidth(btnToRegister));
        panel.add(space(10));
        panel.add(loginStatus);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    // ---------------- REGISTER (BoxLayout dọc) ----------------
    private JPanel createRegisterCard() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(8, 8, 8, 8));

        JLabel head = new JLabel("Tạo tài khoản mới");
        head.setFont(head.getFont().deriveFont(Font.BOLD, FONT_SECTION));
        head.setForeground(new Color(32, 40, 70));
        head.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel userLb = label("Tài khoản");
        userLb.setFont(userLb.getFont().deriveFont(FONT_LABEL));
        regUserField = new JTextField();
        regUserField.setFont(regUserField.getFont().deriveFont(FONT_FIELD));
        addEnterToClick(regUserField, this::doRegister);

        JLabel passLb = label("Mật khẩu");
        passLb.setFont(passLb.getFont().deriveFont(FONT_LABEL));
        regPassField = new JPasswordField();
        regPassField.setFont(regPassField.getFont().deriveFont(FONT_FIELD));
        addEnterToClick(regPassField, this::doRegister);

        JCheckBox show = new JCheckBox("Hiện mật khẩu");
        show.setFont(show.getFont().deriveFont(FONT_LABEL - 2f));
        bindShowPassword(regPassField, show);
        show.setAlignmentX(Component.LEFT_ALIGNMENT);
        show.setOpaque(false);

        RoundedButton btnRegister = new RoundedButton("Tạo tài khoản");
        btnRegister.setFont(btnRegister.getFont().deriveFont(Font.BOLD, FONT_BUTTON));
        btnRegister.addActionListener(e -> doRegister());

        RoundedButton btnBack = new RoundedButton("Quay lại đăng nhập");
        btnBack.setFont(btnBack.getFont().deriveFont(Font.BOLD, FONT_BUTTON));
        btnBack.setBackground(new Color(235, 239, 245));
        btnBack.setForeground(new Color(32, 40, 70));
        btnBack.addActionListener(e -> {
            loginUserField.setText("");
            loginPassField.setText("");
            loginStatus.setText(" ");
            cardLayout.show(cardContainer, CARD_LOGIN);
        });

        regStatus = new JLabel(" ");
        regStatus.setFont(regStatus.getFont().deriveFont(FONT_LABEL - 2f));
        regStatus.setForeground(new Color(180, 50, 50));
        regStatus.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(head);
        panel.add(space(16));
        panel.add(userLb);
        panel.add(space(8));
        panel.add(stylize(regUserField));
        panel.add(space(16));
        panel.add(passLb);
        panel.add(space(8));
        panel.add(stylize(regPassField));
        panel.add(space(10));
        panel.add(show);
        panel.add(space(22));
        panel.add(fillWidth(btnRegister));
        panel.add(space(14));
        panel.add(fillWidth(btnBack));
        panel.add(space(10));
        panel.add(regStatus);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    // ---------------- Actions ----------------
    private void doLogin() {
        String user = loginUserField.getText().trim();
        String pass = new String(loginPassField.getPassword());

        // NEW: kiểm tra không dấu & không khoảng trắng
        String err = validateAsciiNoSpace(user, pass, false);
        if (err != null) { loginStatus.setText(err); return; }

        if (checkLogin(user, pass)) {
            loginStatus.setForeground(new Color(30, 140, 70));
            loginStatus.setText("Đăng nhập thành công!");
            JOptionPane.showMessageDialog(this, "Login OK. Bắt đầu game!");
            dispose();
            // new ArkanoidGame();
        } else {
            loginStatus.setForeground(new Color(180, 50, 50));
            loginStatus.setText("Sai tài khoản hoặc mật khẩu.");
        }
    }

    private void doRegister() {
        String user = regUserField.getText().trim();
        String pass = new String(regPassField.getPassword());

        // NEW: kiểm tra không dấu & không khoảng trắng + độ dài + ký tự ';'
        String err = validateAsciiNoSpace(user, pass, true);
        if (err != null) { regStatus.setText(err); return; }

        if (userExists(user)) {
            regStatus.setText("Tài khoản đã tồn tại.");
            return;
        }
        if (saveAccount(user, pass)) {
            regStatus.setForeground(new Color(30, 140, 70));
            regStatus.setText("Tạo tài khoản thành công! Quay lại đăng nhập.");
        } else {
            regStatus.setForeground(new Color(180, 50, 50));
            regStatus.setText("Lỗi khi lưu tài khoản.");
        }
    }

    // ---------------- Validation helpers ----------------
    /**
     * Kiểm tra:
     * - Không rỗng
     * - Không có khoảng trắng (space, tab, newline…)
     * - Không có ký tự ngoài ASCII (nghĩa là không dấu)
     * - Không chứa ';' (để khỏi hỏng định dạng file)
     * - Nếu isRegister = true: yêu cầu user>=3, pass>=4
     * @return thông điệp lỗi (nếu có), null nếu hợp lệ
     */
    private String validateAsciiNoSpace(String user, String pass, boolean isRegister) {
        if (user.isEmpty() || pass.isEmpty()) return "Vui lòng nhập đầy đủ thông tin.";

        if (containsWhitespace(user) || containsWhitespace(pass))
            return "Không dùng khoảng trắng (spaces) trong tài khoản/mật khẩu.";

        if (!isAscii(user) || !isAscii(pass))
            return "Không dùng dấu/ký tự có dấu trong tài khoản/mật khẩu.";

        if (user.indexOf(';') >= 0 || pass.indexOf(';') >= 0)
            return "Không dùng ký tự ';' trong tài khoản/mật khẩu.";

        if (isRegister) {
            if (user.length() < 3) return "Tài khoản tối thiểu 3 ký tự.";
            if (pass.length() < 4) return "Mật khẩu tối thiểu 4 ký tự.";
        }
        return null;
    }

    private boolean containsWhitespace(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (Character.isWhitespace(s.charAt(i))) return true;
        }
        return false;
    }

    private boolean isAscii(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) > 127) return false; // non-ASCII => có dấu/ký tự đặc biệt hệ Unicode
        }
        return true;
    }

    // ---------------- File helpers ----------------
    private void ensureAccountFile() {
        try { if (!accountFile.exists()) accountFile.createNewFile(); } catch (IOException ignored) {}
    }

    private boolean checkLogin(String user, String pass) {
        try (Scanner sc = new Scanner(accountFile)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(";", 2);
                if (parts.length == 2 && parts[0].equals(user) && parts[1].equals(pass)) return true;
            }
        } catch (IOException ignored) {}
        return false;
    }

    private boolean userExists(String user) {
        try (Scanner sc = new Scanner(accountFile)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(";", 2);
                if (parts.length >= 1 && parts[0].equals(user)) return true;
            }
        } catch (IOException ignored) {}
        return false;
    }

    private boolean saveAccount(String user, String pass) {
        try (FileWriter fw = new FileWriter(accountFile, true)) {
            fw.write(user + ";" + pass + System.lineSeparator());
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    // ---------------- UI helpers ----------------
    private JLabel label(String text) {
        JLabel lb = new JLabel(text);
        lb.setForeground(new Color(50, 60, 80));
        lb.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lb;
    }

    private Component space(int h) { return Box.createRigidArea(new Dimension(0, h)); }

    private JComponent fillWidth(JComponent c) {
        c.setAlignmentX(Component.LEFT_ALIGNMENT);
        c.setMaximumSize(new Dimension(Integer.MAX_VALUE, 56)); // nút cao hơn
        return c;
    }

    private JComponent stylize(JComponent comp) {
        comp.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 235), 1, true),
                new EmptyBorder(14, 16, 14, 16) // padding lớn hơn
        ));
        comp.setBackground(new Color(250, 251, 253));
        comp.setAlignmentX(Component.LEFT_ALIGNMENT);
        comp.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48)); // field cao hơn
        return comp;
    }

    private void addEnterToClick(JComponent field, Runnable action) {
        field.addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) action.run();
            }
        });
    }

    private void bindShowPassword(JPasswordField field, JCheckBox checkbox) {
        final char def = field.getEchoChar();
        checkbox.addActionListener(e ->
                field.setEchoChar(checkbox.isSelected() ? (char) 0 : def)
        );
    }

    // ---------------- Custom components ----------------
    static class GradientPanel extends JPanel {
        private final Color start, end;
        public GradientPanel(Color start, Color end) { this.start = start; this.end = end; }
        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            GradientPaint paint = new GradientPaint(0, 0, start, getWidth(), getHeight(), end);
            g2.setPaint(paint);
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.dispose();
        }
    }

    static class RoundedPanel extends JPanel {
        private final int radius;
        private final Color bg;
        public RoundedPanel(int radius, Color bg) { this.radius = radius; this.bg = bg; setOpaque(false); }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bg);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.setColor(new Color(0, 0, 0, 18));
            g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    static class RoundedButton extends JButton {
        public RoundedButton(String text) {
            super(text);
            setFocusPainted(false);
            setBackground(new Color(32, 113, 214));
            setForeground(Color.WHITE);
            setBorder(new EmptyBorder(14, 20, 14, 20)); // padding lớn để nút cao
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setContentAreaFilled(false);
        }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Color base = getBackground();
            if (getModel().isRollover()) base = base.brighter();
            if (getModel().isArmed()) base = base.darker();
            g2.setColor(base);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
            super.paintComponent(g);
            g2.dispose();
        }
        @Override public void setContentAreaFilled(boolean b) { /* custom paint */ }
    }
}
