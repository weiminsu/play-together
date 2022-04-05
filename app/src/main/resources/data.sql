INSERT INTO ACCOUNTS VALUES (
'b7ed93e6-250c-448f-ae22-f10b7069c289', 'tom', 'tom@abc.com', 'adjfasfe', 'its me mario', 'vermont south', ''
)
ON CONFLICT DO NOTHING;

INSERT INTO CHILDREN VALUES
('4af72e54-42c0-44cf-9b70-be02d6ae3447', 'b7ed93e6-250c-448f-ae22-f10b7069c289', 'Aron', 3),
('cd9976e2-feff-4f1f-a732-b30d02ea2f8e', 'b7ed93e6-250c-448f-ae22-f10b7069c289', 'Brooke', 0)
ON CONFLICT DO NOTHING;