package net.sf.anathema.framework.view.util;

import net.infonode.tabbedpanel.TabDropDownListVisiblePolicy;
import net.infonode.tabbedpanel.TabbedPanel;
import net.infonode.tabbedpanel.TabbedPanelProperties;
import net.infonode.tabbedpanel.titledtab.TitledTab;
import net.infonode.util.Direction;
import net.sf.anathema.lib.gui.IView;
import net.sf.anathema.lib.gui.widgets.RevalidatingScrollPane;

import javax.swing.JComponent;
import javax.swing.JPanel;
import java.awt.FlowLayout;

public class TabbedView implements IView {

  private static void initTabbedPaneProperties(TabbedPanelProperties paneProperties, TabDirection tabDirection) {
    paneProperties.removeSuperObject(paneProperties);
    Direction direction = tabDirection.getDirection();
    paneProperties.setTabAreaOrientation(direction);
    paneProperties.setTabReorderEnabled(false);
    paneProperties.setTabDeselectable(false);
    paneProperties.setEnsureSelectedTabVisible(true);
    paneProperties.setHighlightPressedTab(true);
    paneProperties.setTabDropDownListVisiblePolicy(TabDropDownListVisiblePolicy.NEVER);
    paneProperties.getTabAreaComponentsProperties().setStretchEnabled(true);
  }

  private final TabbedPanel tabbedPane = new TabbedPanel();

  public TabbedView(TabDirection tabDirection) {
    initTabbedPaneProperties(tabbedPane.getProperties(), tabDirection);
  }

  public void addView(IView content, ContentProperties properties) {
    JComponent tabContent = content.getComponent();
    if (properties.isNeedsScrollbar()) {
      JPanel viewComponent = new JPanel(new FlowLayout(FlowLayout.LEFT));
      viewComponent.add(tabContent);
      tabContent = new RevalidatingScrollPane(viewComponent);
    }
    TitledTab tab = new TitledTab(properties.getName(), null, tabContent, null);
    tabbedPane.addTab(tab);
  }

  @Override
  public final JComponent getComponent() {
    return tabbedPane;
  }

  public final void setTabAreaComponents(JComponent... components) {
    tabbedPane.setTabAreaComponents(components);
  }
}
