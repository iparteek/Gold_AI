import React, { useState, useRef, useEffect } from 'react';
import { Container, Row, Col, Card, Form, Button, Alert, Spinner } from 'react-bootstrap';
import { chatAPI } from '../services/api';

const ChatInterface = ({ onSuggestPurchase }) => {
  const [messages, setMessages] = useState([
    {
      type: 'bot',
      content: '🌟 Welcome! I\'m your Gold Investment AI Assistant. Ask me anything about gold investment, current prices, or how to buy digital gold!',
      timestamp: new Date()
    }
  ]);
  const [inputMessage, setInputMessage] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState('');
  const messagesEndRef = useRef(null);

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  };

  useEffect(() => {
    scrollToBottom();
  }, [messages]);

  const handleSendMessage = async (e) => {
    e.preventDefault();
    if (!inputMessage.trim()) return;

    const userMessage = inputMessage.trim();
    setInputMessage('');
    setError('');
    
    const newUserMessage = {
      type: 'user',
      content: userMessage,
      timestamp: new Date()
    };
    setMessages(prev => [...prev, newUserMessage]);
    setIsLoading(true);

    try {
      const response = await chatAPI.sendMessage(userMessage);
      
      const botMessage = {
        type: 'bot',
        content: response.response,
        isGoldQuery: response.goldInvestmentQuery,
        suggestPurchase: response.suggestPurchase,
        goldPrice: response.currentGoldPrice,
        goldFact: response.goldFact,
        timestamp: new Date()
      };
      
      setMessages(prev => [...prev, botMessage]);
      
      if (response.suggestPurchase) {
        setTimeout(() => {
          onSuggestPurchase && onSuggestPurchase();
        }, 2000);
      }
      
    } catch (error) {
      setError(error.message);
      const errorMessage = {
        type: 'bot',
        content: '😔 Sorry, I\'m having trouble responding right now. Please try again in a moment.',
        timestamp: new Date()
      };
      setMessages(prev => [...prev, errorMessage]);
    } finally {
      setIsLoading(false);
    }
  };

  const handleQuickQuestion = (question) => {
    setInputMessage(question);
  };

  const quickQuestions = [
    "What is the current gold price?",
    "How to invest in digital gold?",
    "Benefits of gold investment",
    "How to buy gold online?"
  ];

  return (
    <Container fluid className="h-100">
      <Row className="h-100">
        <Col>
          <Card className="h-100 shadow">
            <Card.Header className="bg-warning text-dark">
              <h5 className="mb-0">
                <i className="bi bi-robot me-2"></i>
                Gold Investment AI Assistant
              </h5>
            </Card.Header>
            
            <Card.Body className="d-flex flex-column" style={{ height: '70vh' }}>
              {error && (
                <Alert variant="danger" onClose={() => setError('')} dismissible>
                  <i className="bi bi-exclamation-triangle me-2"></i>
                  {error}
                </Alert>
              )}
              
              <div className="mb-3">
                <small className="text-muted">Quick questions to try:</small>
                <div className="d-flex flex-wrap gap-2 mt-2">
                  {quickQuestions.map((question, index) => (
                    <Button
                      key={index}
                      variant="outline-warning"
                      size="sm"
                      onClick={() => handleQuickQuestion(question)}
                      disabled={isLoading}
                    >
                      {question}
                    </Button>
                  ))}
                </div>
              </div>

              <div className="flex-grow-1 overflow-auto mb-3 border rounded p-3" style={{ backgroundColor: '#f8f9fa' }}>
                {messages.map((message, index) => (
                  <div key={index} className={`mb-3 d-flex ${message.type === 'user' ? 'justify-content-end' : 'justify-content-start'}`}>
                    <div className={`p-3 rounded-lg shadow-sm ${message.type === 'user' ? 'bg-primary text-white' : 'bg-white'}`} 
                         style={{ maxWidth: '80%' }}>
                      {message.type === 'bot' && (
                        <div className="text-warning mb-2">
                          <i className="bi bi-robot me-2"></i>
                          <small>Gold AI Assistant</small>
                        </div>
                      )}
                      <div style={{ whiteSpace: 'pre-line' }}>{message.content}</div>
                      {message.goldPrice && (
                        <div className="mt-2 p-2 bg-light rounded">
                          <small className="text-muted">
                            💰 Current Gold Price: ₹{message.goldPrice.toFixed(2)}/gram
                          </small>
                        </div>
                      )}
                      <small className="text-muted d-block mt-2">
                        {message.timestamp.toLocaleTimeString()}
                      </small>
                    </div>
                  </div>
                ))}
                
                {isLoading && (
                  <div className="d-flex justify-content-start mb-3">
                    <div className="bg-white p-3 rounded-lg shadow-sm">
                      <Spinner animation="dots" size="sm" className="me-2" />
                      <small className="text-muted">AI is thinking...</small>
                    </div>
                  </div>
                )}
                <div ref={messagesEndRef} />
              </div>

              <Form onSubmit={handleSendMessage}>
                <div className="input-group">
                  <Form.Control
                    type="text"
                    value={inputMessage}
                    onChange={(e) => setInputMessage(e.target.value)}
                    placeholder="Ask me about gold investment..."
                    disabled={isLoading}
                    className="border-warning"
                  />
                  <Button 
                    type="submit" 
                    variant="warning"
                    disabled={isLoading || !inputMessage.trim()}
                  >
                    {isLoading ? (
                      <Spinner size="sm" animation="border" />
                    ) : (
                      <i className="bi bi-send"></i>
                    )}
                  </Button>
                </div>
              </Form>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
};

export default ChatInterface;
