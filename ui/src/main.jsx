
import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import NewsList from './pages/NewsList';
import ReportChart from './pages/ReportChart';

import 'primereact/resources/themes/lara-light-blue/theme.css';
import 'primereact/resources/primereact.min.css';
import 'primeicons/primeicons.css';

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <BrowserRouter>
      <div className="p-4">
        <nav className="flex gap-4 mb-6">
          <Link to="/" className="text-blue-600 underline">Haber Listesi</Link>
          <Link to="/grafik" className="text-blue-600 underline">Rapor Grafik</Link>
        </nav>
        <Routes>
          <Route path="/" element={<NewsList />} />
          <Route path="/grafik" element={<ReportChart />} />
        </Routes>
      </div>
    </BrowserRouter>
  </React.StrictMode>
);
