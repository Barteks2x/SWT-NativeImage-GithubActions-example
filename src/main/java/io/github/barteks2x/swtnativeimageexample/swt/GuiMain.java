package io.github.barteks2x.swtnativeimageexample.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GuiMain {

    private static final Logger LOGGER = Logger.getLogger("GuiMain");

    final Display display;
    final Shell shell;

    public static void main(String[] args) {
        LOGGER.info("Loading GUI...");
        new GuiMain().start();
    }

    private GuiMain() {
        display = new Display();
        shell = new Shell(display);

        shell.addShellListener(ShellListener.shellClosedAdapter(this::onCloseEvent));

        final Image icon = new Image(display, GuiMain.class.getResourceAsStream("/icon.png"));
        shell.setImage(icon);
        shell.setLayout(new GridLayout(3, true));

        Button button = new Button(shell, SWT.NONE);
        button.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
        button.setEnabled(true);

        shell.pack();
        shell.setMinimumSize(shell.getSize());
    }

    private void onCloseEvent(ShellEvent evt) {
        MessageBox box = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
        box.setMessage("Do you want to quit?");
        box.setText("SWT + Native Image example");
        if (box.open() != SWT.YES) {
            evt.doit = false;
        }
    }

    private void start() {
        int errorCode = 0;
        try {
            shell.open();
            while (!shell.isDisposed()) {
                    if (!display.readAndDispatch()) {
                        display.sleep();
                    }
            }
            if (!display.isDisposed()) {
                display.dispose();
            }
        } catch (Throwable t) {
            LOGGER.log(Level.SEVERE, "An unexpected error occurred", t);
            errorCode = 2;
        }
        System.exit(errorCode);
    }
}