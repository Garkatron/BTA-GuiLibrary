package deus.guilib.interfaces;

import deus.guilib.element.config.derivated.ElementConfig;

@FunctionalInterface
public interface IElementConfigLambda<T extends ElementConfig> {
	ElementConfig apply(ElementConfig config);
}
