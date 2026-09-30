import os
import numpy as np
import pandas as pd

from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score, classification_report
from xgboost import XGBClassifier
import joblib


RANDOM_STATE = 42
DATASET_SIZE = 2000

np.random.seed(RANDOM_STATE)


def generate_dataset():

    data = []

    for _ in range(DATASET_SIZE):

        revenue_last_30_days = np.random.uniform(20000, 500000)

        average_transaction_value = np.random.uniform(
            500,
            min(25000, revenue_last_30_days)
        )

        transaction_count = np.random.randint(20, 500)

        revenue_volatility = np.random.uniform(
            100,
            average_transaction_value * 2
        )

        weekend_transaction_ratio = np.random.uniform(0, 1)

        revenue_trend = np.random.uniform(-0.8, 1.0)

        # Synthetic creditworthiness logic.
        # Higher revenue, transaction activity and positive growth
        # indicate stronger creditworthiness.
        # Higher volatility and weekend concentration reduce it.

        creditworthiness_score = (
            0.35 * (revenue_last_30_days / 500000)
            + 0.20 * (transaction_count / 500)
            + 0.15 * (average_transaction_value / 25000)
            + 0.20 * revenue_trend
            - 0.20 * (revenue_volatility / 50000)
            - 0.10 * weekend_transaction_ratio
        )

        probability_of_default = (
            1 / (1 + np.exp(-5 * creditworthiness_score))
        )

        default = np.random.binomial(
            1,
            1 - probability_of_default
        )

        data.append([
            revenue_last_30_days,
            average_transaction_value,
            transaction_count,
            revenue_volatility,
            weekend_transaction_ratio,
            revenue_trend,
            default
        ])

    columns = [
        "revenue_last_30_days",
        "average_transaction_value",
        "transaction_count",
        "revenue_volatility",
        "weekend_transaction_ratio",
        "revenue_trend",
        "default"
    ]

    return pd.DataFrame(data, columns=columns)


def train_model():

    df = generate_dataset()

    os.makedirs("data", exist_ok=True)
    os.makedirs("models", exist_ok=True)

    df.to_csv(
        "data/merchant_credit_training_data.csv",
        index=False
    )

    X = df.drop("default", axis=1)
    y = df["default"]

    X_train, X_test, y_train, y_test = train_test_split(
        X,
        y,
        test_size=0.2,
        random_state=RANDOM_STATE,
        stratify=y
    )

    model = XGBClassifier(
        n_estimators=200,
        max_depth=4,
        learning_rate=0.05,
        subsample=0.8,
        colsample_bytree=0.8,
        random_state=RANDOM_STATE,
        eval_metric="logloss"
    )

    model.fit(X_train, y_train)

    predictions = model.predict(X_test)

    print("Accuracy:", accuracy_score(y_test, predictions))

    print("\nClassification Report:")
    print(classification_report(y_test, predictions))

    joblib.dump(
        model,
        "models/credit_risk_model.pkl"
    )

    print("\nModel saved to models/credit_risk_model.pkl")


if __name__ == "__main__":
    train_model()