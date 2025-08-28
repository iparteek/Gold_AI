import React, { useState } from 'react';
import { ToastContainer } from 'react-toastify';
import Header from './components/Header';
import ChatInterface from './components/ChatInterface';
import PurchaseForm from './components/PurchaseForm';
import Dashboard from './components/Dashboard';
import './App.css';

function App() {
  const [activeTab, setActiveTab] = useState('chat');

  const handleSuggestPurchase = () => {
    setTimeout(() => {
      setActiveTab('purchase');
    }, 1000);
  };

  const renderActiveComponent = () => {
    switch (activeTab) {
      case 'chat':
        return <ChatInterface onSuggestPurchase={handleSuggestPurchase} />;
      case 'purchase':
        return <PurchaseForm />;
      case 'dashboard':
        return <Dashboard />;
      default:
        return <ChatInterface onSuggestPurchase={handleSuggestPurchase} />;
    }
  };

  return (
    <div className="App">
      <Header activeTab={activeTab} setActiveTab={setActiveTab} />
      
      <div className="main-content">
        {renderActiveComponent()}
      </div>

      <ToastContainer
        position="top-right"
        autoClose={5000}
        hideProgressBar={false}
        newestOnTop={false}
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
        theme="light"
      />
    </div>
  );
}

export default App;
