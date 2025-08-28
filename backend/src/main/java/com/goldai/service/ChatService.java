package com.goldai.service;

import com.goldai.model.ChatRequest;
import com.goldai.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

@Service
public class ChatService {

    @Autowired
    private GoldPriceService goldPriceService;

    private final List<String> goldFacts = Arrays.asList(
        "Gold has been used as a store of value for over 2,500 years and remains one of the most trusted investments.",
        "Digital gold allows you to invest in gold starting from just ₹1, making it accessible to everyone.",
        "Gold is considered a hedge against inflation and economic uncertainty, protecting your wealth over time.",
        "India is the world's second-largest consumer of gold, with a rich cultural tradition of gold ownership.",
        "Gold prices are influenced by factors like inflation, currency fluctuations, and global economic conditions.",
        "Digital gold is backed by physical gold stored in secure vaults, giving you real gold ownership.",
        "Gold investment helps diversify your portfolio and reduces overall investment risk.",
        "Unlike physical gold, digital gold has no storage costs, insurance fees, or security concerns.",
        "You can convert digital gold to physical gold or cash instantly through most platforms.",
        "Gold has historically maintained its purchasing power even during economic downturns."
    );

    // Enhanced AI-like response templates
    private final Map<String, String[]> intelligentResponses = new HashMap<>();

    public ChatService() {
        initializeIntelligentResponses();
    }

    public Mono<ChatResponse> processMessage(ChatRequest request) {
        String message = request.getMessage().trim();
        
        // Check if user is responding with YES/NO to investment question
        if (isPurchaseResponse(message)) {
            return handlePurchaseResponse(message);
        }
        
        // Enhanced AI-like classification and response
        return analyzeAndGenerateResponse(message);
    }

    private void initializeIntelligentResponses() {
        intelligentResponses.put("price", new String[]{
            "Great question about gold pricing! The gold market is dynamic and influenced by global economic factors.",
            "Gold prices are a key indicator of market sentiment and economic stability.",
            "Understanding gold prices is crucial for making informed investment decisions."
        });
        
        intelligentResponses.put("investment", new String[]{
            "Gold investment is a time-tested wealth preservation strategy used by investors worldwide.",
            "Smart investors often include gold in their portfolios for diversification and stability.",
            "Gold investment offers unique benefits that other assets simply cannot provide."
        });
        
        intelligentResponses.put("buy", new String[]{
            "Buying gold has never been easier, especially with digital gold platforms.",
            "The gold purchasing process has been revolutionized by digital technology.",
            "Modern gold buying is convenient, secure, and accessible to everyone."
        });
        
        intelligentResponses.put("benefit", new String[]{
            "Gold offers numerous advantages that make it a preferred investment choice.",
            "The benefits of gold investment have been recognized for millennia.",
            "Gold provides unique advantages in portfolio management and wealth protection."
        });
    }

    private boolean isPurchaseResponse(String message) {
        String lowerMessage = message.toLowerCase();
        String[] yesResponses = {"yes", "yeah", "yep", "sure", "okay", "ok", "definitely", "absolutely", "i want to invest", "i want to buy", "let's do it", "proceed", "continue"};
        String[] noResponses = {"no", "nope", "not now", "maybe later", "not interested", "cancel", "decline"};
        
        return Arrays.stream(yesResponses).anyMatch(lowerMessage::contains) || 
               Arrays.stream(noResponses).anyMatch(lowerMessage::contains);
    }

    private Mono<ChatResponse> handlePurchaseResponse(String message) {
        String lowerMessage = message.toLowerCase();
        String[] yesResponses = {"yes", "yeah", "yep", "sure", "okay", "ok", "definitely", "absolutely", "i want to invest", "i want to buy", "let's do it", "proceed", "continue"};
        
        boolean wantsToBuy = Arrays.stream(yesResponses).anyMatch(lowerMessage::contains);
        
        if (wantsToBuy) {
            return goldPriceService.getCurrentGoldPrice()
                    .map(goldPrice -> new ChatResponse(
                        "🎉 **Excellent Choice!** 🎉\n\n" +
                        "You've made a smart decision to invest in gold! Here's what happens next:\n\n" +
                        "💰 **Current Gold Price:** ₹" + String.format("%.2f", goldPrice) + " per gram\n\n" +
                        "✨ **You will now be redirected to our secure purchase page** where you can:\n" +
                        "• Choose your investment amount (starting from ₹1)\n" +
                        "• Enter your details safely\n" +
                        "• Complete your gold purchase instantly\n" +
                        "• Get your digital gold certificate immediately\n\n" +
                        "🚀 **Get ready to start your gold investment journey!**\n\n" +
                        "**Redirecting you to the purchase page now...**",
                        true, 
                        true,  // This will trigger the redirect
                        "You're about to become a gold investor - welcome to the world of smart investing!",
                        goldPrice
                    ));
        } else {
            return Mono.just(new ChatResponse(
                "No problem at all! 😊\n\n" +
                "Gold investment is a personal choice, and timing is important. Here are some things to consider:\n\n" +
                "📚 **Take your time to:**\n" +
                "• Research more about gold as an investment\n" +
                "• Consider your financial goals\n" +
                "• Start with small amounts when you're ready\n" +
                "• Ask me any questions about gold investment\n\n" +
                "💡 **Remember:** You can invest in digital gold starting from just ₹1, so there's no pressure to invest large amounts.\n\n" +
                "Feel free to ask me anything else about gold, investments, or market trends. I'm here to help! 🤝",
                false, false, null, 0.0
            ));
        }
    }

    private Mono<ChatResponse> analyzeAndGenerateResponse(String message) {
        return goldPriceService.getCurrentGoldPrice()
                .map(goldPrice -> {
                    // Enhanced classification logic
                    String lowerMessage = message.toLowerCase();
                    boolean isGoldRelated = isGoldInvestmentQuery(lowerMessage);
                    
                    if (isGoldRelated) {
                        return generateIntelligentGoldResponse(lowerMessage, goldPrice);
                    } else {
                        return handleGeneralQuery();
                    }
                })
                .onErrorReturn(handleGeneralQuery());
    }

    private boolean isGoldInvestmentQuery(String message) {
        String[] goldKeywords = {
            "gold", "investment", "invest", "precious metal", "digital gold",
            "gold price", "gold rate", "buy gold", "sell gold", "gold market",
            "commodity", "bullion", "gold coins", "gold bars", "portfolio",
            "diversify", "hedge", "inflation", "market", "trading", "wealth",
            "savings", "money", "finance", "economy", "asset"
        };

        return Arrays.stream(goldKeywords).anyMatch(message::contains);
    }

    private ChatResponse generateIntelligentGoldResponse(String message, double goldPrice) {
        String randomFact = goldFacts.get((int) (Math.random() * goldFacts.size()));
        
        // Determine response category
        String category = determineResponseCategory(message);
        String contextualIntro = getIntelligentIntro(category);
        String detailedResponse = generateDetailedResponse(message, goldPrice, randomFact);
        String investmentQuestion = generateInvestmentQuestion();
        
        String fullResponse = contextualIntro + "\n\n" + detailedResponse + "\n\n" + investmentQuestion;
        
        return new ChatResponse(fullResponse, true, false, randomFact, goldPrice);
    }

    private String determineResponseCategory(String message) {
        if (message.contains("price") || message.contains("rate") || message.contains("cost")) {
            return "price";
        } else if (message.contains("buy") || message.contains("purchase") || message.contains("how to")) {
            return "buy";
        } else if (message.contains("benefit") || message.contains("advantage") || message.contains("why")) {
            return "benefit";
        } else {
            return "investment";
        }
    }

    private String getIntelligentIntro(String category) {
        String[] intros = intelligentResponses.get(category);
        if (intros != null) {
            return intros[(int) (Math.random() * intros.length)];
        }
        return "That's an excellent question about gold investment!";
    }

    private String generateDetailedResponse(String message, double goldPrice, String fact) {
        String lowerMessage = message.toLowerCase();
        
        if (lowerMessage.contains("price") || lowerMessage.contains("rate") || lowerMessage.contains("cost")) {
            return String.format(
                "💰 **Live Gold Price: ₹%.2f per gram**\n\n" +
                "📊 **Market Insight:** %s\n\n" +
                "Gold prices fluctuate based on:\n" +
                "• Global economic conditions\n" +
                "• Currency exchange rates\n" +
                "• Inflation expectations\n" +
                "• Geopolitical events\n" +
                "• Supply and demand dynamics\n\n" +
                "Digital gold allows you to invest at real-time market prices without storage concerns.",
                goldPrice, fact
            );
        } else if (lowerMessage.contains("how to buy") || lowerMessage.contains("purchase") || lowerMessage.contains("buy")) {
            return String.format(
                "🛒 **How to Buy Digital Gold - Simple & Secure**\n\n" +
                "💰 **Current Price:** ₹%.2f per gram\n" +
                "🎯 **Smart Fact:** %s\n\n" +
                "**Your Digital Gold Journey:**\n" +
                "1️⃣ **Choose Amount** → Start from just ₹1\n" +
                "2️⃣ **Secure Details** → Quick KYC verification\n" +
                "3️⃣ **Safe Payment** → Multiple payment options\n" +
                "4️⃣ **Instant Gold** → Digital certificate in seconds\n\n" +
                "**Why Choose Digital Gold:**\n" +
                "✅ No storage fees ✅ 24/7 trading ✅ Instant liquidity",
                goldPrice, fact
            );
        } else if (lowerMessage.contains("benefit") || lowerMessage.contains("advantage") || lowerMessage.contains("why")) {
            return String.format(
                "🌟 **Powerful Benefits of Gold Investment**\n\n" +
                "💰 **Current Market Price:** ₹%.2f per gram\n" +
                "💡 **Investment Wisdom:** %s\n\n" +
                "**🔥 Top Investment Benefits:**\n" +
                "🛡️ **Inflation Shield** → Protects purchasing power\n" +
                "📊 **Portfolio Balance** → Reduces overall risk\n" +
                "💎 **Instant Liquidity** → Convert to cash anytime\n" +
                "🏛️ **Crisis Protection** → Safe haven asset\n" +
                "🇮🇳 **Cultural Value** → Respected in Indian tradition\n" +
                "⚡ **Digital Convenience** → No physical storage needed",
                goldPrice, fact
            );
        } else {
            return String.format(
                "🌟 **Smart Gold Investment Strategy** 🌟\n\n" +
                "💰 **Real-Time Price:** ₹%.2f per gram\n" +
                "📚 **Expert Insight:** %s\n\n" +
                "**🎯 Why Investors Choose Gold:**\n" +
                "• **Stability** in volatile markets\n" +
                "• **Diversification** for balanced portfolios\n" +
                "• **Accessibility** through digital platforms\n" +
                "• **Flexibility** to buy/sell anytime\n" +
                "• **Security** of precious metal backing\n\n" +
                "Digital gold bridges traditional wealth preservation with modern convenience!",
                goldPrice, fact
            );
        }
    }

    private String generateInvestmentQuestion() {
        String[] questions = {
            "💭 **Ready to start your gold investment journey?** \n✨ You can begin with any amount - even just ₹1! \n**Simply reply 'Yes' to proceed or 'No' to continue learning**",
            "🤔 **Would you like to purchase digital gold right now?** \n🎯 It's quick, secure, and completely hassle-free! \n**Just say 'Yes' to invest or 'No' if you'd prefer to wait**",
            "🚀 **Interested in adding gold to your investment portfolio today?** \n💎 Digital gold makes it incredibly easy to get started! \n**Type 'Yes' to buy now or 'No' to explore more**",
            "⭐ **Want to experience the convenience of digital gold investment?** \n📱 The entire process takes less than 2 minutes! \n**Reply 'Yes' to begin or 'No' to ask more questions**"
        };
        
        return questions[(int) (Math.random() * questions.length)];
    }

    private ChatResponse handleGeneralQuery() {
        String generalResponse = 
            "🌟 **Welcome to Gold Investment AI!** 🌟\n\n" +
            "I'm your intelligent gold investment advisor, powered by advanced AI algorithms. I can help you with:\n\n" +
            "💰 **Live gold prices** and market analysis\n" +
            "📈 **Investment strategies** and portfolio advice\n" +
            "💎 **Digital gold** buying and selling guidance\n" +
            "🏪 **Market insights** and trend predictions\n" +
            "📊 **Risk assessment** and diversification tips\n\n" +
            "**🎯 Ask me about:**\n" +
            "• 'What is the current gold price?'\n" +
            "• 'How should I invest in gold?'\n" +
            "• 'What are the benefits of gold investment?'\n" +
            "• 'Is now a good time to buy gold?'\n\n" +
            "**How can I assist with your gold investment decisions today?** 🤖";

        return new ChatResponse(generalResponse, false, false, null, 0.0);
    }
}
