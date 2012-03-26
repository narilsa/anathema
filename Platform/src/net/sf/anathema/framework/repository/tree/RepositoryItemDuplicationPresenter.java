package net.sf.anathema.framework.repository.tree;

import net.disy.commons.core.message.Message;
import net.disy.commons.core.message.MessageType;
import net.disy.commons.swing.action.SmartAction;
import net.sf.anathema.framework.item.IItemType;
import net.sf.anathema.framework.messaging.IAnathemaMessaging;
import net.sf.anathema.framework.presenter.resources.FileUi;
import net.sf.anathema.framework.repository.RepositoryException;
import net.sf.anathema.framework.repository.access.IRepositoryFileAccess;
import net.sf.anathema.framework.view.PrintNameFile;
import net.sf.anathema.lib.control.change.IChangeListener;
import net.sf.anathema.lib.gui.Presenter;
import net.sf.anathema.lib.logging.Logger;
import net.sf.anathema.lib.resources.IResources;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static net.disy.commons.swing.dialog.message.MessageDialogFactory.showMessageDialog;

public class RepositoryItemDuplicationPresenter implements Presenter {

  private final IResources resources;
  private final RepositoryTreeModel model;
  private final RepositoryTreeView view;
  private final IAnathemaMessaging messaging;

  public RepositoryItemDuplicationPresenter(IResources resources, RepositoryTreeModel repositoryTreeModel,
                                            RepositoryTreeView treeView, IAnathemaMessaging messaging) {
    this.resources = resources;
    this.model = repositoryTreeModel;
    this.view = treeView;
    this.messaging = messaging;
  }

  @Override
  public void initPresentation() {
    final SmartAction action = new SmartAction(resources.getString("AnathemaCore.Tools.RepositoryView.DuplicateName"),
            //$NON-NLS-1$
            new FileUi(resources).getDuplicateFileIcon()) {
      private static final long serialVersionUID = 6140625217947542252L;

      @Override
      protected void execute(Component parentComponent) {
        try {
          PrintNameFile[] printNameFiles = model.getPrintNameFilesInSelection();
          for (PrintNameFile printNameFile : printNameFiles) {
            String id = printNameFile.getRepositoryId();
            IItemType type = printNameFile.getItemType();
            IRepositoryFileAccess readAccess = model.getFileAccess(printNameFile);
            String mainFilePath = model.getMainFilePath(type, id);
            RepositoryImportHandler handler = new RepositoryImportHandler(model, type, id);
            for (File file : readAccess.getFiles()) {
              InputStream inputStream = readAccess.openInputStream(file);
              handler.importStream(mainFilePath, inputStream, file.getPath());
              inputStream.close();
            }
            model.refreshItem(type, handler.getNewId());
            messaging.addMessage("AnathemaCore.Tools.RepositoryView.DuplicateDoneMessage", MessageType.INFORMATION); //$NON-NLS-1$
          }
        }
        catch (RepositoryException e) {
          showMessageDialog(parentComponent, new Message(
              resources.getString("AnathemaCore.Tools.RepositoryView.RepositoryError"), //$NON-NLS-1$
              e));
          Logger.getLogger(getClass()).error(e);
        }
        catch (IOException e) {
          showMessageDialog(parentComponent, new Message(
              resources.getString("AnathemaCore.Tools.RepositoryView.FileError"), //$NON-NLS-1$
              e));
          Logger.getLogger(getClass()).error(e);
        }
      }
    };
    action.setToolTipText(resources.getString("AnathemaCore.Tools.RepositoryView.DuplicateToolTip")); //$NON-NLS-1$
    view.addActionButton(action);
    model.addTreeSelectionChangeListener(new IChangeListener() {
      @Override
      public void changeOccurred() {
        action.setEnabled(model.getPrintNameFilesInSelection().length == 1);
      }
    });
    action.setEnabled(false);
  }
}
