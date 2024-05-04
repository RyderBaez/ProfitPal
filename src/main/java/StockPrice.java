import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//USED https://www.jsonschema2pojo.org/ to format
public class StockPrice {

	@SerializedName("c")
	@Expose
	private Double c;
	@SerializedName("h")
	@Expose
	private Double h;
	@SerializedName("l")
	@Expose
	private Double l;
	@SerializedName("o")
	@Expose
	private Double o;
	@SerializedName("pc")
	@Expose
	private Double pc;
	@SerializedName("t")
	@Expose
	private Integer t;

	public Double getC() {
		return c;
	}

	public void setC(Double c) {
		this.c = c;
	}

	public Double getH() {
		return h;
	}

	public void setH(Double h) {
		this.h = h;
	}

	public Double getL() {
		return l;
	}

	public void setL(Double l) {
		this.l = l;
	}

	public Double getO() {
		return o;
	}

	public void setO(Double o) {
		this.o = o;
	}

	public Double getPc() {
		return pc;
	}

	public void setPc(Double pc) {
		this.pc = pc;
	}

	public Integer getT() {
		return t;
	}

	public void setT(Integer t) {
		this.t = t;
	}

}
