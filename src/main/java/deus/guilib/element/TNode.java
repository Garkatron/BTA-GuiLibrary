package deus.guilib.element;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.*;

public class TNode implements Node {
	@NotNull
	@Override
	public String getNodeName() {
		return "";
	}

	@Override
	public String getNodeValue() throws DOMException {
		return "";
	}

	@Override
	public void setNodeValue(String nodeValue) throws DOMException {

	}

	@Override
	public short getNodeType() {
		return 0;
	}

	@Override
	public Node getParentNode() {
		return null;
	}

	@NotNull
	@Override
	public NodeList getChildNodes() {
		return null;
	}

	@Override
	public Node getFirstChild() {
		return null;
	}

	@Override
	public Node getLastChild() {
		return null;
	}

	@Override
	public Node getPreviousSibling() {
		return null;
	}

	@Override
	public Node getNextSibling() {
		return null;
	}

	@Override
	public NamedNodeMap getAttributes() {
		return null;
	}

	@Override
	public Document getOwnerDocument() {
		return null;
	}

	@Override
	public Node insertBefore(Node newChild, Node refChild) throws DOMException {
		return null;
	}

	@Override
	public Node replaceChild(Node newChild, Node oldChild) throws DOMException {
		return null;
	}

	@Override
	public Node removeChild(Node oldChild) throws DOMException {
		return null;
	}

	@Override
	public Node appendChild(Node newChild) throws DOMException {
		return null;
	}

	@Override
	public boolean hasChildNodes() {
		return false;
	}

	@Override
	public Node cloneNode(boolean deep) {
		return null;
	}

	@Override
	public void normalize() {

	}

	@Override
	public boolean isSupported(String feature, String version) {
		return false;
	}

	@Override
	public String getNamespaceURI() {
		return "";
	}

	@Override
	public String getPrefix() {
		return "";
	}

	@Override
	public void setPrefix(String prefix) throws DOMException {

	}

	@Override
	public String getLocalName() {
		return "";
	}

	@Override
	public boolean hasAttributes() {
		return false;
	}

	@Override
	public String getBaseURI() {
		return "";
	}

	@Override
	public short compareDocumentPosition(Node other) throws DOMException {
		return 0;
	}

	@Override
	public String getTextContent() throws DOMException {
		return "";
	}

	@Override
	public void setTextContent(String textContent) throws DOMException {

	}

	@Override
	public boolean isSameNode(Node other) {
		return false;
	}

	@Override
	public String lookupPrefix(String namespaceURI) {
		return "";
	}

	@Override
	public boolean isDefaultNamespace(String namespaceURI) {
		return false;
	}

	@Override
	public String lookupNamespaceURI(String prefix) {
		return "";
	}

	@Override
	public boolean isEqualNode(Node arg) {
		return false;
	}

	@Override
	public Object getFeature(String feature, String version) {
		return null;
	}

	@Override
	public Object setUserData(String key, Object data, UserDataHandler handler) {
		return null;
	}

	@Override
	public Object getUserData(String key) {
		return null;
	}
}
