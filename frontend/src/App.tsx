import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import axios from 'axios';
import React, { Suspense } from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import BusInfo from './components/pages/BusInfo';
import Join from './components/pages/Join';
import Login from './components/pages/Login';
import MyPage from './components/pages/MyPage';
import Main from './components/pages/Main';
import Spinner from './utils/Spinner';
import { ErrorBoundary } from './utils/ErrorBoundary';
import Menu from './components/pages/Menu';
import interceptResponse from './utils/interceptResponse';

function App() {
  const queryClient = new QueryClient({
    defaultOptions: {
      queries: {
        suspense: true,
      },
    },
  });

  axios.defaults.baseURL = 'https://www.inside-ssafy.com/';
  interceptResponse();

  return (
    <ErrorBoundary>
      <Suspense fallback={<Spinner />}>
        <QueryClientProvider client={queryClient}>
          <BrowserRouter>
            <Routes>
              <Route path="/" element={<Main />} />
              <Route path="/login" element={<Login />} />
              <Route path="/join" element={<Join />} />
              <Route path="/businfo" element={<BusInfo />} />
              <Route path="/mypage" element={<MyPage />} />
              <Route path="/menu" element={<Menu />} />
            </Routes>
          </BrowserRouter>
        </QueryClientProvider>
      </Suspense>
    </ErrorBoundary>
  );
}

export default App;
