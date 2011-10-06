module Petals
  module Helpers
    def petals_file(url, dir)
      file_name = component_url.split("/").last

      #download zip file
      remote_file "#{node['petals']['install_dir']}/#{dir}#{file_name}" do
        source component_url
        action :nothing
      end

      #install only if it has changed
      http_request "HEAD #{component_url}" do
        message ""
        url component_url
        action :head
        if File.exists?("#{node['petals']['install_dir']}#{dir}#{file_name}")
          headers "If-Modified-Since" => File.mtime("#{node['petals']['install_dir']}#{dir}#{file_name}").httpdate
        end
        notifies :create, resources(:remote_file => "#{node['petals']['install_dir']}#{dir}#{file_name}"), :immediately
      end
    end
  end
end
