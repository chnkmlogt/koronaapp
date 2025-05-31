import React, { useEffect, useState, useRef } from 'react';
import { InputTextarea } from 'primereact/inputtextarea';
import { Button } from 'primereact/button';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Paginator } from 'primereact/paginator';
import { Menu } from 'primereact/menu';
import { Toast } from 'primereact/toast';
import axios from 'axios';

function NewsList() {
  const [haberMetni, setHaberMetni] = useState('');
  const [haberler, setHaberler] = useState([]);
  const [first, setFirst] = useState(0);
  const [rows, setRows] = useState(20);
  const [totalRecords, setTotalRecords] = useState(0);
  const toast = useRef(null);

  const menuItems = [
    { label: 'Haber Girişi', command: () => window.scrollTo({ top: 0, behavior: 'smooth' }) },
    { label: 'Haber Tablosu', command: () => document.getElementById('haber-tablosu').scrollIntoView({ behavior: 'smooth' }) }
  ];

  const haberiKaydet = async () => {
    if (!haberMetni.trim()) return;
    try {
      await axios.post('/api/news', haberMetni, {
        headers: { 'Content-Type': 'text/plain' }
      });
      setHaberMetni('');
      toast.current.show({ severity: 'success', summary: 'Başarılı', detail: 'Haber başarıyla kaydedildi.' });
      haberListesiniGetir();
    } catch (err) {
      toast.current.show({ severity: 'error', summary: 'Hata', detail: 'Haber gönderilemedi.' });
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
      toast.current.show({ severity: 'error', summary: 'Hata', detail: 'Haber listesi alınamadı.' });
    }
  };

  useEffect(() => {
    haberListesiniGetir();
  }, [first, rows]);

  return (
      <div className="p-4">
        <Toast ref={toast} />

        <h2 className="text-xl font-bold mb-4">Haber Girişi ve Listeleme</h2>

        <div className="mb-4">
          <Menu model={menuItems} />
        </div>

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

        <div id="haber-tablosu">
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
      </div>
  );
}

export default NewsList;
