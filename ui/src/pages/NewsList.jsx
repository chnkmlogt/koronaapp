
import React, { useEffect, useState } from 'react';
import { InputTextarea } from 'primereact/inputtextarea';
import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Paginator } from 'primereact/paginator';
import axios from 'axios';

function NewsList() {
  const [haberMetni, setHaberMetni] = useState('');
  const [haberler, setHaberler] = useState([]);
  const [first, setFirst] = useState(0);
  const [rows, setRows] = useState(5);
  const [totalRecords, setTotalRecords] = useState(0);

  const haberiKaydet = async () => {
    if (!haberMetni.trim()) return;
    try {
      await axios.post('/api/news', haberMetni, {
        headers: { 'Content-Type': 'text/plain' }
      });
      setHaberMetni('');
      haberListesiniGetir();
    } catch (err) {
      console.error('Haber gönderilemedi:', err);
    }
  };

  const haberListesiniGetir = async () => {
    try {
      const res = await axios.get('/api/news', {
        params: {
          page: first / rows,
          size: rows
        }
      });
      const data = res.data;
      const content = Array.isArray(data.content) ? data.content : data;
      setHaberler(content);
      setTotalRecords(data.totalElements || content.length);
    } catch (err) {
      console.error('Haber listesi alınamadı:', err);
    }
  };

  useEffect(() => {
    haberListesiniGetir();
  }, [first, rows]);

  return (
    <div className="p-4">
      <h2 className="text-xl font-bold mb-4">Haber Girişi ve Listeleme</h2>

      <div className="mb-4">
        <InputTextarea
          value={haberMetni}
          onChange={(e) => setHaberMetni(e.target.value)}
          rows={3}
          cols={60}
          placeholder="Yeni haber metni girin..."
          autoResize
        />
        <Button
          label="Haberi Gönder"
          icon="pi pi-send"
          onClick={haberiKaydet}
          className="mt-2"
        />
      </div>

      <DataTable value={haberler} responsiveLayout="scroll">
        <Column field="date" header="Tarih"></Column>
        <Column field="city" header="Şehir"></Column>
        <Column field="cases" header="Vaka"></Column>
        <Column field="deaths" header="Vefat"></Column>
        <Column field="recovered" header="İyileşen"></Column>
        <Column field="rawText" header="Haber Metni"></Column>
      </DataTable>

      <Paginator
        first={first}
        rows={rows}
        totalRecords={totalRecords}
        onPageChange={(e) => {
          setFirst(e.first);
          setRows(e.rows);
        }}
        className="mt-4"
      />
    </div>
  );
}

export default NewsList;
