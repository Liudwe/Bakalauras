
**Response example**:

```
{
  "id": 1,
  "minAbsoluteTerm": 2,
  "maxAbsoluteTerm": 6,
  "minAbsolutePrincipalAmount": {
    "amount": "150.00",
    "currency": "EUR"
  },
  "maxAbsolutePrincipalAmount": {
    "amount": "2000.00",
    "currency": "EUR"
  },
  "options": [
    {
      "id": 1,
      "term": 2,
      "minPrincipalAmount": {
        "amount": "150.00",
        "currency": "EUR"
      },
      "maxPrincipalAmount": {
        "amount": "492.00",
        "currency": "EUR"
      }
    }
  ]
}
```

**POST body example**:
```
{
  "principal": {
    "amount": 178,
    "currency": "EUR"
  },
  "term": 2
}
```
**Response example**:

```
{
  "annualInterestRate": 0.15,
  "annualPercentageRate": 36.82,
  "monthlyPaymentAmount": {
    "amount": "104.03",
    "currency": "EUR"
  },
  "fees": {
    "amount": "30.05",
    "currency": "EUR"
  },
  "totalAmount": {
    "amount": "208.05",
    "currency": "EUR"
  }
}
```

