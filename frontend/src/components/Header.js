import React from 'react';
import { Navbar, Nav, Container } from 'react-bootstrap';

const Header = ({ activeTab, setActiveTab }) => {
  return (
    <Navbar bg="warning" variant="dark" expand="lg" className="mb-4 shadow">
      <Container>
        <Navbar.Brand className="fw-bold text-dark">
          <i className="bi bi-gem me-2"></i>
          Gold Investment AI
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto">
            <Nav.Link 
              className={`text-dark fw-semibold ${activeTab === 'chat' ? 'active' : ''}`}
              onClick={() => setActiveTab('chat')}
            >
              <i className="bi bi-chat-dots me-1"></i>
              Chat Assistant
            </Nav.Link>
            <Nav.Link 
              className={`text-dark fw-semibold ${activeTab === 'purchase' ? 'active' : ''}`}
              onClick={() => setActiveTab('purchase')}
            >
              <i className="bi bi-cart-plus me-1"></i>
              Buy Gold
            </Nav.Link>
            <Nav.Link 
              className={`text-dark fw-semibold ${activeTab === 'dashboard' ? 'active' : ''}`}
              onClick={() => setActiveTab('dashboard')}
            >
              <i className="bi bi-graph-up me-1"></i>
              My Portfolio
            </Nav.Link>
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};

export default Header;
