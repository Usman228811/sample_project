package com.expense.manager.plan.smapleproject.presentation.screens.premium

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.expense.manager.plan.smapleproject.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun SubscriptionScreen(
    subscriptionViewModel: SubscriptionViewModel = koinViewModel()
) {

    val activity = LocalActivity.current as Activity
    val state by subscriptionViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        subscriptionViewModel.loadProducts(activity)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {

            PremiumHeader()

            Column(modifier = Modifier.padding(16.dp)) {

                SectionTitle(stringResource(R.string.subscription_plans))

                Spacer(Modifier.height(12.dp))

                SubscriptionOption(
                    title = stringResource(R.string.subscription_remove_ads),
                    price = state.removeAdsPrice,
                    isSelected = state.selectedButtonPos == 0,
                    onClick = { subscriptionViewModel.updateSelectedButtonPos(0) }
                )

                Spacer(Modifier.height(10.dp))

                SubscriptionOption(
                    title = stringResource(R.string.subscription_feature_1),
                    price = state.feature1Price,
                    isSelected = state.selectedButtonPos == 1,
                    onClick = { subscriptionViewModel.updateSelectedButtonPos(1) }
                )

                Spacer(Modifier.height(10.dp))

                SubscriptionOption(
                    title = stringResource(R.string.subscription_feature_2),
                    price = state.feature2Price,
                    isSelected = state.selectedButtonPos == 2,
                    onClick = { subscriptionViewModel.updateSelectedButtonPos(2) }
                )

                Spacer(Modifier.height(10.dp))

                SubscriptionOption(
                    title = stringResource(R.string.subscription_feature_3),
                    price = state.feature3Price,
                    isSelected = state.selectedButtonPos == 3,
                    onClick = { subscriptionViewModel.updateSelectedButtonPos(3) }
                )

                Spacer(Modifier.height(20.dp))

                PrimaryButton(
                    text = stringResource(state.buttonText),
                    onClick = { subscriptionViewModel.purchase(activity) }
                )

                Spacer(Modifier.height(32.dp))

                SectionTitle(stringResource(R.string.subscription_lifetime_plan))

                Spacer(Modifier.height(12.dp))

                LifetimeCard(
                    price = state.oneTimePrice,
                    buttonText = stringResource(state.buttonTextLifeTime),
                    onClick = { subscriptionViewModel.purchaseProduct(activity) }
                )

                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun PremiumHeader() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .statusBarsPadding()
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(64.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {

            Icon(
                imageVector = Icons.Default.WorkspacePremium,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(36.dp)
            )
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.subscription_premium_plans),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )

        Spacer(Modifier.height(6.dp))

        Text(
            text = stringResource(R.string.subscription_header_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun SubscriptionOption(
    title: String,
    price: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceContainer
            }
        ),
        border = if (isSelected) {
            BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
        } else {
            null
        }
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {

                SelectionDot(isSelected = isSelected)

                Spacer(Modifier.size(12.dp))

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    color = if (isSelected) {
                        MaterialTheme.colorScheme.onPrimaryContainer
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    }
                )
            }

            Text(
                text = price,
                style = MaterialTheme.typography.titleSmall,
                color = if (isSelected) {
                    MaterialTheme.colorScheme.onPrimaryContainer
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
        }
    }
}

@Composable
private fun SelectionDot(isSelected: Boolean) {

    Box(
        modifier = Modifier
            .size(22.dp)
            .background(
                color = if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.surfaceContainerHigh
                },
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {

        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(14.dp)
            )
        }
    }
}

@Composable
private fun LifetimeCard(
    price: String,
    buttonText: String,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = price,
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = stringResource(R.string.subscription_lifetime_caption),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(20.dp))

            PrimaryButton(
                text = buttonText,
                onClick = onClick
            )
        }
    }
}

@Composable
private fun PrimaryButton(
    text: String,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = MaterialTheme.shapes.medium
    ) {

        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
private fun SectionTitle(title: String) {

    Row(verticalAlignment = Alignment.CenterVertically) {

        Box(
            modifier = Modifier
                .size(width = 4.dp, height = 18.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
        )

        Spacer(modifier = Modifier.size(8.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}
