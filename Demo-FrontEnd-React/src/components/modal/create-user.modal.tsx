import { App, Input, Modal } from "antd";
import { useState } from "react";
import { createAUserAPI } from "../../services/api";
import axios from "axios";

interface IProps {
  openCreateModal: boolean;
  setOpenCreateModal: (v: boolean) => void;
  getAllUsers: () => Promise<void>;
}
const CreateUserModal = (props: IProps) => {
  const { notification, message } = App.useApp();
  const { openCreateModal, setOpenCreateModal, getAllUsers } = props;
  const [name, setName] = useState<string>("");
  const [email, setEmail] = useState<string>("");
  const [loading, setLoading] = useState<boolean>(false);

  const handleSubmit = async () => {
    setLoading(true);
    try {
      const response = await createAUserAPI(name, email);
      if (response.data.data) {
        message.success("Tạo mới user thành công!");
        setName("");
        setEmail("");
        setOpenCreateModal(false);
        await getAllUsers();
      }
    } catch (error: unknown) {
      let messageError = "";
      if (axios.isAxiosError(error)) {
        messageError = error.response?.data?.message ?? "unknown!";
      }
      console.log(">>> check error: ", error);
      notification.error({
        message: "Có lỗi xảy ra!",
        description: messageError
          .split("\n")
          .map((item, index) => <div key={index}>{item}</div>),
      });
    } finally {
      setLoading(false);
    }
  };

  return (
    <Modal
      title="Create a new user"
      maskClosable={false}
      closable={{ "aria-label": "Custom Close Button" }}
      open={openCreateModal}
      onOk={handleSubmit}
      onCancel={() => {
        setName("");
        setEmail("");
        setOpenCreateModal(false);
      }}
      okText={"Create"}
      okButtonProps={{
        loading: loading,
      }}
    >
      <div
        style={{
          display: "flex",
          flexDirection: "column",
          gap: 10,
          marginBottom: 15,
        }}
      >
        <span>Name: </span>
        <Input value={name} onChange={(v) => setName(v.target.value)} />
      </div>
      <div style={{ display: "flex", flexDirection: "column", gap: 10 }}>
        <span>Email: </span>
        <Input value={email} onChange={(v) => setEmail(v.target.value)} />
      </div>
    </Modal>
  );
};

export default CreateUserModal;
