package deus.guilib.interfaces;

import deus.guilib.element.config.Config;
import deus.guilib.element.config.derivated.ElementConfig;
import deus.guilib.interfaces.element.IElement;

@FunctionalInterface
public interface IConfigLambda<T extends ElementConfig> {
	ElementConfig execute(ElementConfig config);
}
