import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api/v1';

const api = axios.create({
  baseURL: BASE_URL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
});

api.interceptors.request.use(
  (config) => {
    console.log('Making request:', config.method?.toUpperCase(), config.url);
    return config;
  },
  (error) => {
    console.error('Request error:', error);
    return Promise.reject(error);
  }
);

api.interceptors.response.use(
  (response) => {
    console.log('Response received:', response.status, response.config.url);
    return response;
  },
  (error) => {
    console.error('Response error:', error.response?.status, error.message);
    return Promise.reject(error);
  }
);

export const chatAPI = {
  sendMessage: async (message, userId = 'anonymous') => {
    try {
      const response = await api.post('/chat/message', {
        message,
        userId
      });
      return response.data;
    } catch (error) {
      console.error('Chat API error:', error);
      throw new Error(error.response?.data?.message || 'Failed to send message');
    }
  },

  checkHealth: async () => {
    try {
      const response = await api.get('/chat/health');
      return response.data;
    } catch (error) {
      throw new Error('Chat service is unavailable');
    }
  }
};

export const purchaseAPI = {
  buyGold: async (purchaseData) => {
    try {
      const response = await api.post('/purchase/gold', purchaseData);
      return response.data;
    } catch (error) {
      console.error('Purchase API error:', error);
      const errorMessage = error.response?.data?.message || 'Failed to purchase gold';
      throw new Error(errorMessage);
    }
  },

  getUserDetails: async (email) => {
    try {
      const response = await api.get(`/purchase/user/${encodeURIComponent(email)}`);
      return response.data;
    } catch (error) {
      if (error.response?.status === 404) {
        return null;
      }
      throw new Error('Failed to fetch user details');
    }
  },

  checkHealth: async () => {
    try {
      const response = await api.get('/purchase/health');
      return response.data;
    } catch (error) {
      throw new Error('Purchase service is unavailable');
    }
  }
};

export default api;
