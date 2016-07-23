package Labels;

public abstract class LabelFactory {
	Label lb = null;
	abstract Label genLabel(String label);
}
