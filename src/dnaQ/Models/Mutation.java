package dnaQ.Models;

public class Mutation {

//	everything string except for id and position

	//common
	public Integer usertestid;
	public String chr;
	public Integer pos;
	public String ref;
	public String alt;
	public String comment;

	//g1000 aka pop-freq
	public String freqid;
	public String globalFreq;
	public String americanFreq;
	public String asianFreq;
	public String afrFreq;
	public String eurFreq;

	//cosmic aka cancer
	public String cancerid;
	public String cancerCount;

	//clinical aka clinvar
	public String clinicalid;
	public String clinicalDisease;
	public String signficance;

	//biology
	public String gene;
	public String biologyDisease;
	public String publicationCount;


	//custom
	public boolean isSelected;


	public Mutation(Integer usertestid, String chr, Integer pos, String ref, String alt,
					String freqid, String globalFreq, String americanFreq, String asianFreq, String afrFreq, String eurFreq,
					String cancerid, String cancerCount,
					String clinicalid, String clinicalDisease, String signficance,
					String gene, String biologyDisease, String publicationCount, String comment) {
		this.usertestid = usertestid;
		this.chr = chr;
		this.pos = pos;
		this.ref = ref;
		this.alt = alt;
		this.freqid = freqid;
		this.globalFreq = globalFreq;
		this.americanFreq = americanFreq;
		this.asianFreq = asianFreq;
		this.afrFreq = afrFreq;
		this.eurFreq = eurFreq;
		this.cancerid = cancerid;
		this.cancerCount = cancerCount;
		this.clinicalid = clinicalid;
		this.clinicalDisease = clinicalDisease;
		this.signficance = signficance;
		this.gene = gene;
		this.biologyDisease = biologyDisease;
		this.publicationCount = publicationCount;
		this.comment = comment;
	}
	public Integer getUsertestid() {
		return usertestid;
	}

	public void setUsertestid(Integer usertestid) {
		this.usertestid = usertestid;
	}

	public String getChr() {
		return chr;
	}

	public void setChr(String chr) {
		this.chr = chr;
	}

	public Integer getPos() {
		return pos;
	}

	public void setPos(Integer pos) {
		this.pos = pos;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getFreqid() {
		return freqid;
	}

	public void setFreqid(String freqid) {
		this.freqid = freqid;
	}

	public String getGlobalFreq() {
		return globalFreq;
	}

	public void setGlobalFreq(String globalFreq) {
		this.globalFreq = globalFreq;
	}

	public String getAmericanFreq() {
		return americanFreq;
	}

	public void setAmericanFreq(String americanFreq) {
		this.americanFreq = americanFreq;
	}

	public String getAsianFreq() {
		return asianFreq;
	}

	public void setAsianFreq(String asianFreq) {
		this.asianFreq = asianFreq;
	}

	public String getAfrFreq() {
		return afrFreq;
	}

	public void setAfrFreq(String afrFreq) {
		this.afrFreq = afrFreq;
	}

	public String getEurFreq() {
		return eurFreq;
	}

	public void setEurFreq(String eurFreq) {
		this.eurFreq = eurFreq;
	}

	public String getCancerid() {
		return cancerid;
	}

	public void setCancerid(String cancerid) {
		this.cancerid = cancerid;
	}

	public String getCancerCount() {
		return cancerCount;
	}

	public void setCancerCount(String cancerCount) {
		this.cancerCount = cancerCount;
	}

	public String getClinicalid() {
		return clinicalid;
	}

	public void setClinicalid(String clinicalid) {
		this.clinicalid = clinicalid;
	}

	public String getClinicalDisease() {
		return clinicalDisease;
	}

	public void setClinicalDisease(String clinicalDisease) {
		this.clinicalDisease = clinicalDisease;
	}

	public String getSignficance() {
		return signficance;
	}

	public void setSignficance(String signficance) {
		this.signficance = signficance;
	}

	public String getGene() {
		return gene;
	}

	public void setGene(String gene) {
		this.gene = gene;
	}

	public String getBiologyDisease() {
		return biologyDisease;
	}

	public void setBiologyDisease(String biologyDisease) {
		this.biologyDisease = biologyDisease;
	}

	public String getPublicationCount() {
		return publicationCount;
	}

	public void setPublicationCount(String publicationCount) {
		this.publicationCount = publicationCount;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean selected) {
		isSelected = selected;
	}

	public int getColumnCount() {
		return getClass().getDeclaredFields().length;
	}
}
