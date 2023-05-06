const currency = (value: number, currency = "HUF", locale = "hu-HU", decimals = 0) => {
	return new Intl.NumberFormat(locale, {
		style: "currency",
		currency,
		minimumFractionDigits: decimals,
	}).format(value)
}

export default currency
