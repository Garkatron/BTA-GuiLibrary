package deus.guilib.container;

import java.util.List;

public interface IContainerComponent {
	List<ContainerComponent> getChildren();
	ContainerComponent setChildren(ContainerComponent...children);
	ContainerComponent addChildren(ContainerComponent...children);
	ContainerComponent build();
}
