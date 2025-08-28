import React, { useState } from 'react';
import { Container, Row, Col, Card, Form, Button, Alert, Spinner, Modal } from 'react-bootstrap';
import { purchaseAPI } from '../services/api';
import { toast } from 'react-toastify';

const PurchaseForm = () => {
  const [formData, setFormData] = useState({
    userName: '',
    email: '',
    phone: '',
    amount: ''
  });
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState('');
  const [showConfirmation, setShowConfirmation] = useState(false);
  const [purchaseResult, setPurchaseResult] = useState(null);

  const currentGoldPrice = 5525.50;

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const calculateGoldAmount = () => {
    if (!formData.amount) return 0;
    return (parseFloat(formData.amount) / currentGoldPrice).toFixed(4);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    if (!formData.userName || !formData.email || !formData.phone || !formData.amount) {
      setError('Please fill in all fields');
      return;
    }

    if (parseFloat(formData.amount) < 1) {
      setError('Minimum investment amount is ₹1');
      return;
    }

    if (!/^\d{10}$/.test(formData.phone)) {
      setError('Please enter a valid 10-digit phone number');
      return;
    }

    setShowConfirmation(true);
  };

  const confirmPurchase = async () => {
    setIsLoading(true);
    setShowConfirmation(false);

    try {
      const purchaseData = {
        userId: `user_${Date.now()}`,
        userName: formData.userName,
        email: formData.email,
        phone: formData.phone,
        amount: parseFloat(formData.amount)
      };

      const result = await purchaseAPI.buyGold(purchaseData);
      setPurchaseResult(result);
      
      toast.success(`🎉 Successfully purchased ${result.goldPurchased} grams of gold!`, {
        position: "top-right",
        autoClose: 5000,
      });

      setFormData({
        userName: '',
        email: '',
        phone: '',
        amount: ''
      });

    } catch (error) {
      setError(error.message);
      toast.error(`❌ Purchase failed: ${error.message}`, {
        position: "top-right",
        autoClose: 5000,
      });
    } finally {
      setIsLoading(false);
    }
  };

  const presetAmounts = [100, 500, 1000, 2000, 5000];

  return (
    <Container>
      <Row className="justify-content-center">
        <Col lg={8}>
          <Card className="shadow">
            <Card.Header className="bg-warning text-dark">
              <h4 className="mb-0">
                <i className="bi bi-cart-plus me-2"></i>
                Buy Digital Gold
              </h4>
            </Card.Header>
            <Card.Body>
              <Alert variant="info" className="mb-4">
                <Row>
                  <Col md={6}>
                    <h6 className="mb-1">💰 Current Gold Price</h6>
                    <h4 className="mb-0 text-primary">₹{currentGoldPrice.toFixed(2)} per gram</h4>
                  </Col>
                  <Col md={6} className="text-end">
                    <small className="text-muted">Last updated: {new Date().toLocaleTimeString()}</small>
                    <div className="text-success">
                      <i className="bi bi-arrow-up me-1"></i>
                      Live pricing
                    </div>
                  </Col>
                </Row>
              </Alert>

              {error && (
                <Alert variant="danger" onClose={() => setError('')} dismissible>
                  {error}
                </Alert>
              )}

              {purchaseResult && (
                <Alert variant="success" className="mb-4">
                  <h6>✅ Purchase Successful!</h6>
                  <p className="mb-1"><strong>Transaction ID:</strong> {purchaseResult.transactionId}</p>
                  <p className="mb-1"><strong>Gold Purchased:</strong> {purchaseResult.goldPurchased} grams</p>
                  <p className="mb-0"><strong>Amount Paid:</strong> ₹{purchaseResult.amountPaid}</p>
                </Alert>
              )}

              <Form onSubmit={handleSubmit}>
                <Row>
                  <Col md={6}>
                    <Form.Group className="mb-3">
                      <Form.Label>
                        <i className="bi bi-person me-2"></i>
                        Full Name *
                      </Form.Label>
                      <Form.Control
                        type="text"
                        name="userName"
                        value={formData.userName}
                        onChange={handleInputChange}
                        placeholder="Enter your full name"
                        disabled={isLoading}
                      />
                    </Form.Group>
                  </Col>
                  <Col md={6}>
                    <Form.Group className="mb-3">
                      <Form.Label>
                        <i className="bi bi-envelope me-2"></i>
                        Email Address *
                      </Form.Label>
                      <Form.Control
                        type="email"
                        name="email"
                        value={formData.email}
                        onChange={handleInputChange}
                        placeholder="Enter your email"
                        disabled={isLoading}
                      />
                    </Form.Group>
                  </Col>
                </Row>

                <Row>
                  <Col md={6}>
                    <Form.Group className="mb-3">
                      <Form.Label>
                        <i className="bi bi-phone me-2"></i>
                        Phone Number *
                      </Form.Label>
                      <Form.Control
                        type="tel"
                        name="phone"
                        value={formData.phone}
                        onChange={handleInputChange}
                        placeholder="Enter 10-digit phone number"
                        disabled={isLoading}
                      />
                    </Form.Group>
                  </Col>
                  <Col md={6}>
                    <Form.Group className="mb-3">
                      <Form.Label>
                        <i className="bi bi-currency-rupee me-2"></i>
                        Investment Amount (₹) *
                      </Form.Label>
                      <Form.Control
                        type="number"
                        name="amount"
                        value={formData.amount}
                        onChange={handleInputChange}
                        placeholder="Minimum ₹1"
                        min="1"
                        step="1"
                        disabled={isLoading}
                      />
                    </Form.Group>
                  </Col>
                </Row>

                <div className="mb-3">
                  <Form.Label>Quick Amount Selection:</Form.Label>
                  <div className="d-flex flex-wrap gap-2">
                    {presetAmounts.map(amount => (
                      <Button
                        key={amount}
                        variant="outline-warning"
                        size="sm"
                        onClick={() => setFormData(prev => ({ ...prev, amount: amount.toString() }))}
                        disabled={isLoading}
                      >
                        ₹{amount}
                      </Button>
                    ))}
                  </div>
                </div>

                {formData.amount && (
                  <Alert variant="success" className="mb-3">
                    <Row>
                      <Col>
                        <strong>You will get: {calculateGoldAmount()} grams of gold</strong>
                      </Col>
                      <Col className="text-end">
                        <small>Value: ₹{formData.amount}</small>
                      </Col>
                    </Row>
                  </Alert>
                )}

                <div className="d-grid">
                  <Button
                    type="submit"
                    variant="warning"
                    size="lg"
                    disabled={isLoading}
                    className="fw-bold"
                  >
                    {isLoading ? (
                      <>
                        <Spinner size="sm" className="me-2" />
                        Processing Purchase...
                      </>
                    ) : (
                      <>
                        <i className="bi bi-gem me-2"></i>
                        Buy Gold Now
                      </>
                    )}
                  </Button>
                </div>
              </Form>

              <Card className="mt-4 border-warning">
                <Card.Body className="text-center">
                  <h6 className="text-warning">🛡️ Why Choose Digital Gold?</h6>
                  <Row className="text-center">
                    <Col md={3}>
                      <i className="bi bi-shield-check text-success h4"></i>
                      <div><small>100% Secure</small></div>
                    </Col>
                    <Col md={3}>
                      <i className="bi bi-lightning text-warning h4"></i>
                      <div><small>Instant Purchase</small></div>
                    </Col>
                    <Col md={3}>
                      <i className="bi bi-bank text-info h4"></i>
                      <div><small>No Storage Cost</small></div>
                    </Col>
                    <Col md={3}>
                      <i className="bi bi-arrow-repeat text-primary h4"></i>
                      <div><small>Easy to Sell</small></div>
                    </Col>
                  </Row>
                </Card.Body>
              </Card>
            </Card.Body>
          </Card>
        </Col>
      </Row>

      <Modal show={showConfirmation} onHide={() => setShowConfirmation(false)} centered>
        <Modal.Header closeButton>
          <Modal.Title>Confirm Purchase</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <h6>Purchase Summary:</h6>
          <ul className="list-unstyled">
            <li><strong>Name:</strong> {formData.userName}</li>
            <li><strong>Email:</strong> {formData.email}</li>
            <li><strong>Phone:</strong> {formData.phone}</li>
            <li><strong>Investment:</strong> ₹{formData.amount}</li>
            <li><strong>Gold Amount:</strong> {calculateGoldAmount()} grams</li>
            <li><strong>Rate:</strong> ₹{currentGoldPrice.toFixed(2)} per gram</li>
          </ul>
          <Alert variant="info">
            Are you sure you want to purchase {calculateGoldAmount()} grams of gold for ₹{formData.amount}?
          </Alert>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setShowConfirmation(false)}>
            Cancel
          </Button>
          <Button variant="warning" onClick={confirmPurchase}>
            Confirm Purchase
          </Button>
        </Modal.Footer>
      </Modal>
    </Container>
  );
};

export default PurchaseForm;
