from fastapi import FastAPI
from pydantic import BaseModel
import joblib
import pandas as pd
import shap

app = FastAPI(title="Merchant Credit Scoring Service")

model = joblib.load("models/credit_risk_model.pkl")
explainer = shap.TreeExplainer(model)


class MerchantFeatures(BaseModel):
    merchantId: int
    revenueLast30Days: float
    averageTransactionValue: float
    transactionCount: int
    revenueVolatility: float
    weekendTransactionRatio: float
    revenueTrend: float


class FeatureContribution(BaseModel):
    feature: str
    contribution: float


class CreditScoreResponse(BaseModel):
    merchantId: int
    creditScore: int
    recommendedLoanAmount: float
    riskLevel: str
    featureContributions: list[FeatureContribution]


@app.get("/health")
def health_check():
    return {"status": "ML service is running"}


@app.post("/score-merchant", response_model=CreditScoreResponse)
def score_merchant(features: MerchantFeatures):

    input_data = pd.DataFrame([{
        "revenue_last_30_days": features.revenueLast30Days,
        "average_transaction_value": features.averageTransactionValue,
        "transaction_count": features.transactionCount,
        "revenue_volatility": features.revenueVolatility,
        "weekend_transaction_ratio": features.weekendTransactionRatio,
        "revenue_trend": features.revenueTrend
    }])

    default_probability = model.predict_proba(input_data)[0][1]

    credit_score = round(850 - (default_probability * 550))
    credit_score = max(300, min(850, credit_score))

    if credit_score >= 700:
        risk_level = "LOW"
        loan_factor = 0.25
    elif credit_score >= 550:
        risk_level = "MEDIUM"
        loan_factor = 0.15
    else:
        risk_level = "HIGH"
        loan_factor = 0.05

    recommended_loan_amount = round(
        features.revenueLast30Days * loan_factor,
        2
    )

    shap_values = explainer.shap_values(input_data)

    if isinstance(shap_values, list):
        contributions = shap_values[1][0]
    else:
        contributions = shap_values[0]

    feature_contributions = [
        FeatureContribution(
            feature=feature,
            contribution=round(float(contribution), 4)
        )
        for feature, contribution in zip(
            input_data.columns,
            contributions
        )
    ]

    feature_contributions.sort(
        key=lambda item: abs(item.contribution),
        reverse=True
    )

    return CreditScoreResponse(
        merchantId=features.merchantId,
        creditScore=credit_score,
        recommendedLoanAmount=recommended_loan_amount,
        riskLevel=risk_level,
        featureContributions=feature_contributions
    )