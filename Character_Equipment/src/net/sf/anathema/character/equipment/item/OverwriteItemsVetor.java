package net.sf.anathema.character.equipment.item;

import java.awt.Component;

import net.disy.commons.core.message.IMessage;
import net.disy.commons.core.message.Message;
import net.disy.commons.core.message.MessageType;
import net.disy.commons.swing.action.ActionConfiguration;
import net.disy.commons.swing.action.IActionConfiguration;
import net.disy.commons.swing.dialog.core.IDialogResult;
import net.disy.commons.swing.dialog.message.MessageUserDialogConfiguration;
import net.disy.commons.swing.dialog.userdialog.UserDialog;
import net.disy.commons.swing.dialog.userdialog.buttons.DialogButtonConfiguration;
import net.sf.anathema.lib.resources.IResources;

public class OverwriteItemsVetor {

  private final Component parentComponent;
  private final IResources resources;

  public OverwriteItemsVetor(Component parentComponent, IResources resources) {
    this.parentComponent = parentComponent;
    this.resources = resources;
  }

  public boolean vetos() {
    String messageText = resources.getString("Equipment.Creation.OverwriteMessage.Text"); //$NON-NLS-1$
    IMessage message = new Message(messageText, MessageType.WARNING);
    MessageUserDialogConfiguration configuration = new MessageUserDialogConfiguration(
        message,
        new DialogButtonConfiguration() {
          @Override
          public IActionConfiguration getOkActionConfiguration() {
            return new ActionConfiguration(resources.getString("Equipment.Creation.OverwriteMessage.OKButton")); //$NON-NLS-1$
          }
        });
    UserDialog userDialog = new UserDialog(parentComponent, configuration);
    IDialogResult result = userDialog.show();
    return result.isCanceled();
  }

}
