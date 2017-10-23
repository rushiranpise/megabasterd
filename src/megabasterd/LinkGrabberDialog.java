package megabasterd;

import java.awt.Dialog;
import java.awt.Frame;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import static megabasterd.MainPanel.THREAD_POOL;
import static megabasterd.MiscTools.BASE642Bin;
import static megabasterd.MiscTools.Bin2BASE64;
import static megabasterd.MiscTools.bin2i32a;
import static megabasterd.MiscTools.extractMegaLinksFromString;
import static megabasterd.MiscTools.extractStringFromClipboardContents;
import static megabasterd.MiscTools.swingReflectionInvoke;
import static megabasterd.MiscTools.swingReflectionInvokeAndWait;
import static megabasterd.MiscTools.truncateText;

public final class LinkGrabberDialog extends javax.swing.JDialog implements ClipboardChangeObserver {

    private boolean _download;
    private String _download_path;
    private final ClipboardSpy _clipboardspy;
    private final MainPanel _main_panel;
    private volatile String _last_selected_account;
    private boolean _remember_master_pass;

    public boolean isRemember_master_pass() {
        return _remember_master_pass;
    }

    public JComboBox<String> getUse_mega_account_down_combobox() {
        return use_mega_account_down_combobox;
    }

    public JButton getDance_button() {
        return dance_button;
    }

    public boolean isDownload() {
        return _download;
    }

    public String getDownload_path() {
        return _download_path;
    }

    public JTextArea getLinks_textarea() {
        return links_textarea;
    }

    public LinkGrabberDialog(java.awt.Frame parent, boolean modal, String download_path, ClipboardSpy clipboardspy) {

        super(parent, modal);
        _download = false;

        initComponents();

        _download_path = download_path;

        _clipboardspy = clipboardspy;

        _last_selected_account = null;

        _remember_master_pass = true;

        swingReflectionInvoke("setText", download_dir_label, truncateText(download_path, 80));

        _main_panel = ((MainPanelView) parent).getMain_panel();

        if (_main_panel.isUse_mega_account_down() && _main_panel.getMega_accounts().size() > 0) {

            for (Object o : _main_panel.getMega_accounts().keySet()) {

                swingReflectionInvoke("addItem", use_mega_account_down_combobox, o);
            }
            
            swingReflectionInvoke("addItem", use_mega_account_down_combobox, "");
            
        } else {
            swingReflectionInvoke("setEnabled", use_mega_account_down_combobox, false);
            swingReflectionInvoke("setEnabled", use_mega_account_down_label, false);
            swingReflectionInvoke("setVisible", use_mega_account_down_combobox, false);
            swingReflectionInvoke("setVisible", use_mega_account_down_label, false);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        links_scrollpane = new javax.swing.JScrollPane();
        links_textarea = new javax.swing.JTextArea();
        dance_button = new javax.swing.JButton();
        links_label = new javax.swing.JLabel();
        change_dir_button = new javax.swing.JButton();
        down_dir_to_label = new javax.swing.JLabel();
        download_dir_label = new javax.swing.JLabel();
        dlc_button = new javax.swing.JButton();
        use_mega_account_down_label = new javax.swing.JLabel();
        use_mega_account_down_combobox = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("LinkGrabber");
        setModal(true);

        links_textarea.setColumns(20);
        links_textarea.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        links_textarea.setRows(5);
        links_textarea.setDoubleBuffered(true);
        links_scrollpane.setViewportView(links_textarea);
        links_textarea.addMouseListener(new ContextMenuMouseListener());

        dance_button.setBackground(new java.awt.Color(102, 204, 255));
        dance_button.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        dance_button.setForeground(new java.awt.Color(255, 255, 255));
        dance_button.setText("Let's dance, baby");
        dance_button.setDoubleBuffered(true);
        dance_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dance_buttonActionPerformed(evt);
            }
        });

        links_label.setFont(new java.awt.Font("Ubuntu", 1, 20)); // NOI18N
        links_label.setText("Put your MEGA/MegaCrypter/ELC link/s here (one per line):");
        links_label.setDoubleBuffered(true);

        change_dir_button.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        change_dir_button.setText("Change it");
        change_dir_button.setDoubleBuffered(true);
        change_dir_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                change_dir_buttonActionPerformed(evt);
            }
        });

        down_dir_to_label.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        down_dir_to_label.setText("Download to: ");
        down_dir_to_label.setDoubleBuffered(true);

        download_dir_label.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N

        dlc_button.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        dlc_button.setText("Load DLC container");
        dlc_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dlc_buttonActionPerformed(evt);
            }
        });

        use_mega_account_down_label.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        use_mega_account_down_label.setText("Use this account for download:");

        use_mega_account_down_combobox.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        use_mega_account_down_combobox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                use_mega_account_down_comboboxItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(links_scrollpane)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(change_dir_button)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(down_dir_to_label)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(download_dir_label, javax.swing.GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dance_button))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(links_label)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dlc_button))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(use_mega_account_down_label)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(use_mega_account_down_combobox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(links_label)
                    .addComponent(dlc_button))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(links_scrollpane, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(use_mega_account_down_label)
                    .addComponent(use_mega_account_down_combobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(change_dir_button)
                    .addComponent(down_dir_to_label)
                    .addComponent(download_dir_label)
                    .addComponent(dance_button, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dance_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dance_buttonActionPerformed

        _download = true;

        this.setVisible(false);
    }//GEN-LAST:event_dance_buttonActionPerformed

    private void change_dir_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_change_dir_buttonActionPerformed

        javax.swing.JFileChooser filechooser = new javax.swing.JFileChooser();

        filechooser.setCurrentDirectory(new java.io.File(_download_path));
        filechooser.setDialogTitle("Download directory");
        filechooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
        filechooser.setAcceptAllFileFilterUsed(false);

        if (filechooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {

            File file = filechooser.getSelectedFile();

            _download_path = file.getAbsolutePath();

            download_dir_label.setText(truncateText(_download_path, 80));
        }
    }//GEN-LAST:event_change_dir_buttonActionPerformed

    private void dlc_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dlc_buttonActionPerformed

        dlc_button.setText("Loading DLC, please wait...");

        dlc_button.setEnabled(false);

        links_textarea.setEnabled(false);

        dance_button.setEnabled(false);

        javax.swing.JFileChooser filechooser = new javax.swing.JFileChooser();

        filechooser.setDialogTitle("Select DLC container");

        filechooser.setFileSelectionMode(javax.swing.JFileChooser.FILES_ONLY);

        filechooser.addChoosableFileFilter(new FileNameExtensionFilter("DLC", "dlc"));

        filechooser.setAcceptAllFileFilterUsed(false);

        if (filechooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {

            final File file = filechooser.getSelectedFile();

            THREAD_POOL.execute(new Runnable() {
                @Override
                public void run() {

                    try (FileInputStream is = new FileInputStream(file)) {

                        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

                            byte[] buffer = new byte[MainPanel.DEFAULT_BYTE_BUFFER_SIZE];

                            int reads;

                            while ((reads = is.read(buffer)) != -1) {

                                out.write(buffer, 0, reads);
                            }

                            String dlc = new String(out.toByteArray());

                            Set<String> links = CryptTools.decryptDLC(dlc, ((MainPanelView) getParent()).getMain_panel());

                            for (Iterator<String> i = links.iterator(); i.hasNext();) {

                                String link = i.next();

                                if (MiscTools.findFirstRegex("(?:https?|mega)://[^/]*/(#.*?)?!.+![^\r\n]+", link, 0) == null) {

                                    i.remove();
                                }
                            }

                            if (!links.isEmpty()) {

                                swingReflectionInvoke("setText", links_textarea, "");

                                for (Iterator<String> i = links.iterator(); i.hasNext();) {

                                    swingReflectionInvoke("append", links_textarea, i.next());

                                    if (i.hasNext()) {
                                        swingReflectionInvoke("append", links_textarea, "\r\n");
                                    }
                                }
                            }
                        }

                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(LinkGrabberDialog.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(LinkGrabberDialog.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    swingReflectionInvoke("setText", dlc_button, "Load DLC container");

                    swingReflectionInvoke("setEnabled", dlc_button, true);

                    swingReflectionInvoke("setEnabled", links_textarea, true);

                    swingReflectionInvoke("setEnabled", dance_button, true);

                }
            });

        } else {

            dlc_button.setText("Load DLC container");

            dlc_button.setEnabled(true);

            links_textarea.setEnabled(true);

            dance_button.setEnabled(true);
        }
    }//GEN-LAST:event_dlc_buttonActionPerformed

    private void use_mega_account_down_comboboxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_use_mega_account_down_comboboxItemStateChanged
        String selected_item = (String) use_mega_account_down_combobox.getSelectedItem();

        if (_main_panel.isUse_mega_account_down() && !"".equals(selected_item) && selected_item != null && !selected_item.equals(_last_selected_account)) {

            use_mega_account_down_combobox.setEnabled(false);

            dance_button.setEnabled(false);

            _last_selected_account = selected_item;

            final String email = selected_item;

            final Dialog tthis = this;

            THREAD_POOL.execute(new Runnable() {
                @Override
                public void run() {

                    HashMap<String, Object> account_info = (HashMap) _main_panel.getMega_accounts().get(email);

                    MegaAPI ma = _main_panel.getMega_active_accounts().get(use_mega_account_down_combobox.getSelectedItem());

                    if (ma == null) {

                        ma = new MegaAPI();

                        String password_aes, user_hash;

                        try {

                            if (_main_panel.getMaster_pass_hash() != null) {

                                if (_main_panel.getMaster_pass() == null) {

                                    GetMasterPasswordDialog dialog = new GetMasterPasswordDialog((Frame) getParent(), true, _main_panel.getMaster_pass_hash(), _main_panel.getMaster_pass_salt());

                                    swingReflectionInvokeAndWait("setLocationRelativeTo", dialog, tthis);

                                    swingReflectionInvokeAndWait("setVisible", dialog, true);

                                    if (dialog.isPass_ok()) {

                                        _main_panel.setMaster_pass(dialog.getPass());

                                        dialog.deletePass();

                                        _remember_master_pass = dialog.getRemember_checkbox().isSelected();

                                        dialog.dispose();

                                        password_aes = Bin2BASE64(CryptTools.aes_cbc_decrypt_pkcs7(BASE642Bin((String) account_info.get("password_aes")), _main_panel.getMaster_pass(), CryptTools.AES_ZERO_IV));

                                        user_hash = Bin2BASE64(CryptTools.aes_cbc_decrypt_pkcs7(BASE642Bin((String) account_info.get("user_hash")), _main_panel.getMaster_pass(), CryptTools.AES_ZERO_IV));

                                    } else {

                                        dialog.dispose();

                                        throw new Exception();
                                    }

                                } else {

                                    password_aes = Bin2BASE64(CryptTools.aes_cbc_decrypt_pkcs7(BASE642Bin((String) account_info.get("password_aes")), _main_panel.getMaster_pass(), CryptTools.AES_ZERO_IV));

                                    user_hash = Bin2BASE64(CryptTools.aes_cbc_decrypt_pkcs7(BASE642Bin((String) account_info.get("user_hash")), _main_panel.getMaster_pass(), CryptTools.AES_ZERO_IV));

                                }

                            } else {

                                password_aes = (String) account_info.get("password_aes");

                                user_hash = (String) account_info.get("user_hash");
                            }

                            ma.fastLogin(email, bin2i32a(BASE642Bin(password_aes)), user_hash);

                            _main_panel.getMega_active_accounts().put(email, ma);

                        } catch (Exception ex) {

                            //getLogger(FileGrabberDialog.class.getName()).log(Level.SEVERE, null, ex);
                            _last_selected_account = null;

                            swingReflectionInvoke("setSelectedIndex", ((LinkGrabberDialog) tthis).getUse_mega_account_down_combobox(), -1);
                        }
                    }

                    swingReflectionInvokeAndWait("setEnabled", ((LinkGrabberDialog) tthis).getUse_mega_account_down_combobox(), true);

                    swingReflectionInvokeAndWait("setEnabled", ((LinkGrabberDialog) tthis).getDance_button(), true);
                }
            });
        } else if(!selected_item.equals(_last_selected_account)) {
            _last_selected_account=null;
        }
    }//GEN-LAST:event_use_mega_account_down_comboboxItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton change_dir_button;
    private javax.swing.JButton dance_button;
    private javax.swing.JButton dlc_button;
    private javax.swing.JLabel down_dir_to_label;
    private javax.swing.JLabel download_dir_label;
    private javax.swing.JLabel links_label;
    private javax.swing.JScrollPane links_scrollpane;
    private javax.swing.JTextArea links_textarea;
    private javax.swing.JComboBox<String> use_mega_account_down_combobox;
    private javax.swing.JLabel use_mega_account_down_label;
    // End of variables declaration//GEN-END:variables

    @Override
    public void notifyClipboardChange() {

        swingReflectionInvoke("setText", links_textarea, extractMegaLinksFromString(extractStringFromClipboardContents(_clipboardspy.getContents())));
    }
}
