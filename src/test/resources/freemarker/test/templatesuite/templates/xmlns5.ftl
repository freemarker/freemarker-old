<#ftl ns_prefixes = {"D": "http://y.com", "xx" : "http://x.com"}>
<#assign r = doc["N:root"]>
${r["N:t1"][0]?default('-')} = No NS
${r["xx:t2"][0]?default('-')} = x NS
${r["t3"][0]?default('-')} = y NS
${r["xx:t4"][0]?default('-')} = x NS
${r["//t1"][0]?default('-')} = No NS
${r["//t2"][0]?default('-')} = -
${r["//t3"][0]?default('-')} = -
${r["//t4"][0]?default('-')} = -
