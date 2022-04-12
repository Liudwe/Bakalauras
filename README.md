# Android onboarding task

Your onboarding task will be to create an application for calculating loan offers. UI example:

![alt text](https://github.com/craftsoft-core/onboarding.android/blob/master/preview.png?raw=true)

## Task description

- You should first query the API for loan offer options in order to configure sliders. Amount slider has min/max absolute amounts
as well as max amount for specific period. Period slider also has min/max period value.
- After loan offer options are saved, calculate offer API should be queried and repayment options should be laid out.
- You should preset default offer which should be based on the slider values positioned in the middle.
- Application should be able to display loan offer options and default offer offline.

## Loan API
### Getting loan options

**URI**: GET `https://www.bio-matic.com/unlokk/offer-options`

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
### Calculating loan offer

**URI**: POST `https://www.bio-matic.com/unlokk/calculate-offer`

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
## Requirements
- the task must be done in **Kotlin**
- data should be stored in **Realm**
- dependency injection should be **Hilt**
- networking should be performed via **Retrofit**, JSON handled via **kotlinx.serialization**
- async tasks should be performed with **Kotlin Coroutines**
- for sliders use this: https://github.com/edgar-zigis/LabeledSeekSlider
