package com.example.ui.screens.family

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FamilyRestroom
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FamilyScreen(
    onRequestGuidance: () -> Unit
) {
    var expandedTip by remember { mutableStateOf<Int?>(0) }

    val parentingTips = listOf(
        Pair(
            "1. 'Günün Nasıl Geçti?' Sorusunu Dönüştürün",
            "Genel sorular yerine: 'Bugün seni en çok güldüren an neydi?', 'Bugün canını sıkan ya da haksızlığa uğradığını hissettiğin bir durum oldu mu?' gibi spesifik sorular çocuğunuzun duygularını açmasını kolaylaştırır."
        ),
        Pair(
            "2. Çocuğunuz Alay Edildiğinde Panikle Değil Sakinlikle Yaklaşın",
            "Öfkeyle 'Sen de ona vursaydın!' demek yerine: 'Bunu yaşamak seni çok üzmüş olmalı, seni anlıyorum. Birlikte sınır çizmeyi ve okul rehberliğiyle çözmeyi planlayalım' güvenini verin."
        ),
        Pair(
            "3. Sınır Çizmenin ve 'Hayır' Demenin Evde Başladığını Unutmayın",
            "Ev ortamında çocuğun kendi sınırlarını (örneğin odasına girilmeden önce kapısının çalınması, istemediği bir temasa zorlanmaması) ifade etmesine izin vermek, okulda akran baskısına karşı 'Hayır' diyebilmesini sağlar."
        ),
        Pair(
            "4. Dijital Dünyasını Yargılamadan Takip Edin",
            "Siber zorbalık sıklıkla evde sessizce yaşanır. Telefonu yasaklamak yerine açık iletişim kanalı kurarak rahatsız olduğu bir mesaj aldığında sizinle çekinmeden paylaşabileceği güveni verin."
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        // Family Header
        item {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Aile & Veli Rehberlik Köprüsü",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "Pozitif ebeveynlik & okul-ev işbirliği",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.size(44.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.FamilyRestroom,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Çocuğunuzun psikolojik refahı okulda olduğu kadar evdeki güvenli iletişimle de şekillenir. Rehberlik servisimizle istediğiniz zaman doğrudan iletişime geçebilirsiniz.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = onRequestGuidance,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Icon(Icons.Default.Phone, contentDescription = null)
                        Spacer(modifier = Modifier.size(8.dp))
                        Text("PDR Servisiyle Görüşme Talep Et")
                    }
                }
            }
        }

        // Bullying Symptoms Checklist for Parents
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary
                        )
                        Text(
                            text = "Evde Fark Edebileceğiniz Zorbalık Belirtileri",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    val parentSigns = listOf(
                        "Pazar akşamları veya sabahları sebepsiz karın/baş ağrıları",
                        "Eve geldiğinde kıyafetlerin veya eşyaların yıpranmış olması",
                        "Telefonuna gelen bildirimlerden sonra ani içe kapanma ve kaygı",
                        "Okul arkadaşlarından bahsetmekten kaçınma veya arkadaş çevresinde ani kopuş"
                    )

                    parentSigns.forEach { sign ->
                        Row(
                            modifier = Modifier.padding(vertical = 2.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text("🌿", fontSize = 14.sp)
                            Text(
                                text = sign,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        // Positive Parenting Guide
        item {
            Text(
                text = "Pozitif Ebeveynlik & Empati İpuçları",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        items(parentingTips.size) { index ->
            val tip = parentingTips[index]
            val isExpanded = expandedTip == index

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expandedTip = if (isExpanded) null else index },
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isExpanded) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
                    else MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = tip.first,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = if (isExpanded) "▲" else "▼",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    AnimatedVisibility(visible = isExpanded) {
                        Text(
                            text = tip.second,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 20.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }
    }
}
