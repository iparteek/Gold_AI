import React, { useState } from 'react';
import { Container, Row, Col, Card, Form, Button, Alert, Table, Spinner } from 'react-bootstrap';
import { purchaseAPI } from '../services/api';

const Dashboard = () => {
  const [email, setEmail] = useState('');
  const [userData, setUserData] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState('');

  const handleSearch = async (e) => {
    e.preventDefault();
    if (!email.trim()) return;

    setIsLoading(true);
    setError('');
    
    try {
      const user = await purchaseAPI.getUserDetails(email.trim());
      setUserData(user);
      if (!user) {
        setError('No user found with this email address.');
      }
    } catch (error) {
      setError(error.message);
      setUserData(null);
    } finally {
      setIsLoading(false);
    }
  };

  const calculateCurrentValue = () => {
    if (!userData) return 0;
    const currentPrice = 5525.50;
    return userData.totalGoldOwned * currentPrice;
  };

  const calculateProfitLoss = () => {
    const currentValue = calculateCurrentValue();
    return currentValue - userData?.totalInvestment || 0;
  };

  return (
    <Container>
      <Row>
        <Col>
          <Card className="shadow">
            <Card.Header className="bg-warning text-dark">
              <h4 className="mb-0">
                <i className="bi bi-graph-up me-2"></i>
                My Gold Portfolio
              </h4>
            </Card.Header>
            <Card.Body>
              <Form onSubmit={handleSearch} className="mb-4">
                <Row className="align-items-end">
                  <Col md={8}>
                    <Form.Group>
                      <Form.Label>Enter your registered email to view portfolio:</Form.Label>
                      <Form.Control
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        placeholder="Enter your email address"
                        disabled={isLoading}
                      />
                    </Form.Group>
                  </Col>
                  <Col md={4}>
                    <Button
                      type="submit"
                      variant="warning"
                      disabled={isLoading || !email.trim()}
                      className="w-100"
                    >
                      {isLoading ? (
                        <>
                          <Spinner size="sm" className="me-2" />
                          Searching...
                        </>
                      ) : (
                        <>
                          <i className="bi bi-search me-2"></i>
                          View Portfolio
                        </>
                      )}
                    </Button>
                  </Col>
                </Row>
              </Form>

              {error && (
                <Alert variant="danger" onClose={() => setError('')} dismissible>
                  {error}
                </Alert>
              )}

              {userData && (
                <>
                  <Card className="mb-4">
                    <Card.Body>
                      <Row>
                        <Col md={6}>
                          <h5 className="text-warning mb-3">
                            <i className="bi bi-person-circle me-2"></i>
                            Account Information
                          </h5>
                          <p><strong>Name:</strong> {userData.userName}</p>
                          <p><strong>Email:</strong> {userData.email}</p>
                          <p><strong>Phone:</strong> {userData.phone}</p>
                          <p><strong>Member Since:</strong> {new Date(userData.createdAt).toLocaleDateString()}</p>
                        </Col>
                        <Col md={6}>
                          <h5 className="text-warning mb-3">
                            <i className="bi bi-gem me-2"></i>
                            Portfolio Summary
                          </h5>
                          <Row>
                            <Col sm={6}>
                              <div className="text-center p-3 bg-light rounded mb-2">
                                <h4 className="text-primary mb-1">{userData.totalGoldOwned.toFixed(4)}</h4>
                                <small className="text-muted">Grams of Gold</small>
                              </div>
                            </Col>
                            <Col sm={6}>
                              <div className="text-center p-3 bg-light rounded mb-2">
                                <h4 className="text-success mb-1">₹{userData.totalInvestment.toFixed(2)}</h4>
                                <small className="text-muted">Total Invested</small>
                              </div>
                            </Col>
                          </Row>
                          <Row>
                            <Col sm={6}>
                              <div className="text-center p-3 bg-light rounded mb-2">
                                <h4 className="text-info mb-1">₹{calculateCurrentValue().toFixed(2)}</h4>
                                <small className="text-muted">Current Value</small>
                              </div>
                            </Col>
                            <Col sm={6}>
                              <div className="text-center p-3 bg-light rounded mb-2">
                                <h4 className={`mb-1 ${calculateProfitLoss() >= 0 ? 'text-success' : 'text-danger'}`}>
                                  ₹{calculateProfitLoss().toFixed(2)}
                                </h4>
                                <small className="text-muted">
                                  {calculateProfitLoss() >= 0 ? 'Profit' : 'Loss'}
                                </small>
                              </div>
                            </Col>
                          </Row>
                        </Col>
                      </Row>
                    </Card.Body>
                  </Card>

                  <Card>
                    <Card.Header>
                      <h5 className="mb-0">
                        <i className="bi bi-clock-history me-2"></i>
                        Transaction History ({userData.purchases.length} transactions)
                      </h5>
                    </Card.Header>
                    <Card.Body>
                      {userData.purchases.length > 0 ? (
                        <div className="table-responsive">
                          <Table striped bordered hover>
                            <thead className="table-warning">
                              <tr>
                                <th>Transaction ID</th>
                                <th>Date</th>
                                <th>Amount (₹)</th>
                                <th>Gold (grams)</th>
                                <th>Price/gram (₹)</th>
                                <th>Status</th>
                              </tr>
                            </thead>
                            <tbody>
                              {userData.purchases
                                .sort((a, b) => new Date(b.purchaseDate) - new Date(a.purchaseDate))
                                .map((purchase, index) => (
                                <tr key={index}>
                                  <td>
                                    <code className="text-primary">{purchase.transactionId}</code>
                                  </td>
                                  <td>{new Date(purchase.purchaseDate).toLocaleDateString()}</td>
                                  <td>₹{purchase.amount.toFixed(2)}</td>
                                  <td>{purchase.grams.toFixed(4)}</td>
                                  <td>₹{purchase.pricePerGram.toFixed(2)}</td>
                                  <td>
                                    <span className="badge bg-success">
                                      <i className="bi bi-check-circle me-1"></i>
                                      {purchase.status}
                                    </span>
                                  </td>
                                </tr>
                              ))}
                            </tbody>
                          </Table>
                        </div>
                      ) : (
                        <Alert variant="info" className="text-center">
                          <i className="bi bi-info-circle me-2"></i>
                          No transactions found. Start investing in gold to see your transaction history here.
                        </Alert>
                      )}
                    </Card.Body>
                  </Card>
                </>
              )}

              <Card className="mt-4 border-warning">
                <Card.Body className="text-center">
                  <h6 className="text-warning">💡 Portfolio Tips</h6>
                  <Row>
                    <Col md={3}>
                      <i className="bi bi-arrow-repeat text-primary h5"></i>
                      <div><small>Regular Investment</small></div>
                    </Col>
                    <Col md={3}>
                      <i className="bi bi-graph-up text-success h5"></i>
                      <div><small>Track Performance</small></div>
                    </Col>
                    <Col md={3}>
                      <i className="bi bi-pie-chart text-info h5"></i>
                      <div><small>Diversify Portfolio</small></div>
                    </Col>
                    <Col md={3}>
                      <i className="bi bi-calendar-check text-warning h5"></i>
                      <div><small>Long-term View</small></div>
                    </Col>
                  </Row>
                </Card.Body>
              </Card>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
};

export default Dashboard;
