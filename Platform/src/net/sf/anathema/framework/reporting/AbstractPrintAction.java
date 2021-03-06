package net.sf.anathema.framework.reporting;

import net.disy.commons.core.io.IOUtilities;
import net.disy.commons.core.message.Message;
import net.disy.commons.core.progress.INonInterruptableRunnableWithProgress;
import net.disy.commons.core.progress.IProgressMonitor;
import net.disy.commons.swing.action.SmartAction;
import net.disy.commons.swing.dialog.progress.ProgressMonitorDialog;
import net.sf.anathema.framework.IAnathemaModel;
import net.sf.anathema.framework.message.MessageUtilities;
import net.sf.anathema.framework.presenter.IItemManagementModelListener;
import net.sf.anathema.framework.repository.IItem;
import net.sf.anathema.lib.resources.IResources;

import javax.swing.KeyStroke;
import java.awt.Component;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static java.awt.Desktop.isDesktopSupported;
import static net.disy.commons.core.progress.IProgressMonitor.UNKNOWN;

public abstract class AbstractPrintAction extends SmartAction {
  public static final String PDF_EXTENSION = ".pdf"; //$NON-NLS-1$
  protected final IAnathemaModel anathemaModel;
  protected final IResources resources;

  public AbstractPrintAction(IAnathemaModel anathemaModel, IResources resources) {
    this.anathemaModel = anathemaModel;
    this.resources = resources;
    setHotKey();
    startEnablingListener();
  }

  private void startEnablingListener() {
    IItemManagementModelListener listener = createEnablingListener();
    anathemaModel.getItemManagement().addListener(listener);
    listener.itemSelected(anathemaModel.getItemManagement().getSelectedItem());
  }

  private void setHotKey() {
    setAcceleratorKey(createKeyStroke());
  }

  protected void printWithProgress(Component parentComponent, final IItem item, final Report selectedReport,
                                   final File selectedFile) throws InvocationTargetException {
    new ProgressMonitorDialog(parentComponent, resources.getString("Anathema.Reporting.Print.Progress.Title")).run(
            //$NON-NLS-1$
            new INonInterruptableRunnableWithProgress() {
              @Override
              public void run(IProgressMonitor monitor) throws InvocationTargetException {
                try {
                  performPrint(monitor, item, selectedReport, selectedFile);
                } catch (ReportException e) {
                  throw new InvocationTargetException(e);
                } catch (IOException e) {
                  throw new InvocationTargetException(e);
                }
              }
            });
  }

  private void performPrint(IProgressMonitor monitor, IItem item, Report selectedReport,
                            File selectedFile) throws IOException, ReportException {
    monitor.beginTask(resources.getString("Anathema.Reporting.Print.Progress.Task"), UNKNOWN); //$NON-NLS-1$
    FileOutputStream stream = null;
    try {
      stream = new FileOutputStream(selectedFile);
      selectedReport.print(item, stream);
    } finally {
      IOUtilities.close(stream);
    }
  }

  protected String getErrorMessage(InvocationTargetException e) {
    if (e.getCause() instanceof FileNotFoundException) {
      return resources.getString("Anathema.Reporting.Message.PrintError.FileOpen"); //$NON-NLS-1$
    } else {
      return resources.getString("Anathema.Reporting.Message.PrintError"); //$NON-NLS-1$
    }
  }

  protected abstract KeyStroke createKeyStroke();

  protected abstract IItemManagementModelListener createEnablingListener();

  protected void handleGeneralException(Component parentComponent, Exception e) {
    String errorMessage = resources.getString("Anathema.Reporting.Message.PrintError"); //$NON-NLS-1$
    MessageUtilities.indicateMessage(getClass(), parentComponent, new Message(errorMessage, e));
  }

  protected void handleInvocationTargetException(Component parentComponent, InvocationTargetException e) {
    String errorMessage = getErrorMessage(e);
    MessageUtilities.indicateMessage(getClass(), parentComponent, new Message(errorMessage, e.getCause()));
  }

  protected void openFile(File selectedFile) throws IOException {
    if (isAutoOpenSupported()) {
      Desktop.getDesktop().open(selectedFile);
    }
  }

  public static boolean isAutoOpenSupported() {
    return isDesktopSupported();
  }
}