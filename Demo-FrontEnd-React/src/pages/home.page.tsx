import { Space, Typography } from "antd";
const { Text } = Typography;

const HomePage = () => {
  return (
    <div style={{ padding: 20 }}>
      <Space direction="vertical">
        <Text mark>Bài tập thực hành FullStack</Text>
        <Text code>- Sử dụng React (TypeScript) để làm giao diện</Text>
        <Text code>- Gọi APIs BackEnd đã code với Spring Boot</Text>
        <Text code>- Hoàn thiện CRUD Users</Text>
      </Space>
    </div>
  );
};

export default HomePage;
