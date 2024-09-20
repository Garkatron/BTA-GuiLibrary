package deus.guilib.interfaces;

import deus.guilib.element.config.derivated.ElementConfig;
import deus.guilib.element.config.derivated.PageGuiConfig;

@FunctionalInterface
public interface IPageGuiConfigLambda<T extends PageGuiConfig> {
	PageGuiConfig apply(PageGuiConfig config);
}
