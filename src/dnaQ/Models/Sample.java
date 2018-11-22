package dnaQ.Models;

public class Sample {

//	everything string except for id and position

	public Integer id;
	public String chr;
	public Integer pos;
	public String ref;
	public String alt;
	public String cosmicid; //same as cosmic-id
	public String cds;
	public String aa;
	public String count;
	public String clinvarid; //same as clinvar-id
	public String clndn;
	public String clnsig;
	public String mc;
	public String origin;
	public String g1000id;
	public String altCount;
	public String totalCount;
	public String altGlobalFreq;
	public String americanFreq;
	public String asianFreq;
	public String afrFreq;
	public String eurFreq;
	public String disease;
	public String drugs;
	public String clinicalSignificance;
	public String evidenceStatement;
	public String variantSummary;
	public String gene;
	public String proteinChange;
	public String oncogenecity;
	public String mutationEffect;

	//custom
	public boolean isSelected;


	public Sample(Integer id,String chr, Integer pos, String ref, String alt, String cosmicid, String cds, String aa, String count, String clinvarid, String clndn,
				  String clnsig, String mc, String origin, String g1000id,String altCount, String totalCount, String altGlobalFreq, String americanFreq,
				  String asianFreq, String afrFreq, String eurFreq,String disease, String drugs,String clinicalSignificance, String evidenceStatement,
				  String variantSummary,String gene, String proteinChange,String oncogenecity, String mutationEffect) {
		this.id = id;
		this.chr = chr;
		this.pos = pos;
		this.ref = ref;
		this.alt = alt;
		this.cosmicid = cosmicid;
		this.cds = cds;
		this.aa = aa;
		this.count = count;
		this.clinvarid = clinvarid;
		this.clndn = clndn;
		this.clnsig = clnsig;
		this.mc = mc;
		this.origin = origin;
		this.g1000id = g1000id;
		this.altCount = altCount;
		this.totalCount = totalCount;
		this.altGlobalFreq = altGlobalFreq;
		this.americanFreq = americanFreq;
		this.asianFreq = asianFreq;
		this.afrFreq = afrFreq;
		this.eurFreq = eurFreq;
		this.disease = disease;
		this.drugs = drugs;
		this.clinicalSignificance = clinicalSignificance;
		this.evidenceStatement = evidenceStatement;
		this.variantSummary = variantSummary;
		this.gene = gene;
		this.proteinChange = proteinChange;
		this.oncogenecity = oncogenecity;
		this.mutationEffect = mutationEffect;

	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getChr() {
		return chr;
	}

	public void setChr(String chr) {
		this.chr = chr;
	}

	public Integer getPos() { return pos; }

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

	public String getCosmicid() {
		return cosmicid;
	}

	public void setCosmicid(String cosmicid) { this.cosmicid = cosmicid; }

	public String getCds() {
		return cds;
	}

	public void setCds(String cds) {
		this.cds = cds;
	}

	public String getAa() {
		return aa;
	}

	public void setAa(String aa) {
		this.aa = aa;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getClinvarid() {
		return clinvarid;
	}

	public void setClinvarid(String clinvarid) {
		this.clinvarid = clinvarid;
	}

	public String getClndn() {
		return clndn;
	}

	public void setClndn(String clndn) {
		this.clndn = clndn;
	}

	public String getClnsig() {
		return clnsig;
	}

	public void setClnsig(String clnsig) {
		this.clnsig = clnsig;
	}

	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getG1000id() {
		return g1000id;
	}

	public void setG1000id(String g1000id) {
		this.g1000id = g1000id;
	}

	public String getAltCount() {
		return altCount;
	}

	public void setAltCount(String altCount) { this.altCount = altCount; }

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public String getAltGlobalFreq() {
		return altGlobalFreq;
	}

	public void setAltGlobalFreq(String altGlobalFreq) {
		this.altGlobalFreq = altGlobalFreq;
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

	public String getDisease() {
		return disease;
	}

	public void setDisease(String disease) {
		this.disease = disease;
	}

	public String getDrugs() {
		return drugs;
	}

	public void setDrugs(String drugs) {
		this.drugs = drugs;
	}

	public String getClinicalSignificance() {
		return clinicalSignificance;
	}

	public void setClinicalSignificance(String clinicalSignificance) {
		this.clinicalSignificance = clinicalSignificance;
	}

	public String getEvidenceStatement() {
		return evidenceStatement;
	}

	public void setEvidenceStatement(String evidenceStatement) {
		this.evidenceStatement = evidenceStatement;
	}

	public String getVariantSummary() {
		return variantSummary;
	}

	public void setVariantSummary(String variantSummary) {
		this.variantSummary = variantSummary;
	}

	public String getGene() {
		return gene;
	}

	public void setGene(String gene) {
		this.gene = gene;
	}

	public String getProteinChange() {
		return proteinChange;
	}

	public void setProteinChange(String proteinChange) {
		this.proteinChange = proteinChange;
	}

	public String getOncogenecity() {
		return oncogenecity;
	}

	public void setOncogenecity(String oncogenecity) {
		this.oncogenecity = oncogenecity;
	}

	public String getMutationEffect() {
		return mutationEffect;
	}

	public void setMutationEffect(String mutationEffect) {
		this.mutationEffect = mutationEffect;
	}

}
